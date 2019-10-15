package br.edu.cruzeirodosul.webapps.canvas.bean;

import br.edu.cruzeirodosul.models.academico.canvas.UsuarioCanvas;
import br.edu.cruzeirodosul.models.academico.canvas.ArquivoCanvas;
import br.edu.cruzeirodosul.util.commons.mail.Email;
import br.edu.cruzeirodosul.util.commons.mail.model.EmailModel;
import br.edu.cruzeirodosul.webapps.webbase.ApplicationBase;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import br.edu.cruzeirodosul.models.academico.canvas.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.net.ftp.FTPFile;

/**
*
* @author lgeovanni
*/
public class EnviaArquivosCanvasBean extends ApplicationBase implements Job {

private final static Logger logger = Logger.getLogger("canvas");
private String fileName;
private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
private String now = sdf.format(new Date());

@Override
public void execute(JobExecutionContext jec) throws JobExecutionException {
if (super.isExecute() || true) {
// if (super.isExecute()) {

try {
SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

List<UsuarioCanvas> alunos = new ArrayList<>();
List<UsuarioCanvas> professor = new ArrayList<>();
List<UsuarioOfertaCanvas> alunosOfertas = new ArrayList<>();
List<UsuarioOfertaCanvas> professorOfertas = new ArrayList<>();
List<UsuarioDadosCanvas> usuarioDados = new ArrayList<>();
List<OfertaCanvas> ofertas = new ArrayList<>();
List<DisciplinaCanvas> disciplinas = new ArrayList<>();

alunos = super.canvasService.findAlunoCanvas();
professor = super.canvasService.findProfessorCanvas();
alunosOfertas = super.canvasService.findAlunoOfertaCanvas();
professorOfertas = super.canvasService.findProfessorOfertaCanvas();
usuarioDados = super.canvasService.findUsuarioDadosCanvas();
ofertas = super.canvasService.findOfertaCanvas();
disciplinas = super.canvasService.findDisciplinaCanvas();

GerarArquivoBean ger = new GerarArquivoBean(alunos, alunosOfertas, professor, professorOfertas, ofertas, disciplinas, usuarioDados);

ger.setArquivos(Arrays.asList(
new ArquivoCanvas(ger.getAluno(), Arrays.asList("user_id,", "login_id,", "password,", "first_name,", "last_name,", "short_name,", "email,", "status\n"), "ALUNOS.csv"),
new ArquivoCanvas(ger.getProfessor(), Arrays.asList("user_id,", "login_id,", "password,", "first_name,", "last_name,", "short_name,", "email,", "status\n"), "PROFESSORES.csv"),
new ArquivoCanvas(ger.getAlunoCriptofrafia(), Arrays.asList("user_id,", "login_id,", "ssha_password,", "first_name,", "last_name,", "short_name,", "email,", "status\n"), "ALUNOS-CRIPTOGRAFIA.csv"),
new ArquivoCanvas(ger.getAlunoOferta(), Arrays.asList("course_id,", "user_id,", "role,", "section_id,", "status\n"), "ALUNOS_X_OFERTAS.csv"),
new ArquivoCanvas(ger.getProfessorOferta(), Arrays.asList("course_id,", "user_id,", "role,", "section_id,", "status\n"), "PROFESSORES_X_OFERTAS.csv"),
new ArquivoCanvas(ger.getAlunoDados(), Arrays.asList("user_id,", "rgm_number,", "polo_number,", "course_name,", "doc_number\n"), "ALUNOS_DADOS.csv"),
new ArquivoCanvas(ger.getOferta(), Arrays.asList("section_id,", "course_id,", "name,", "status\n"), "OFERTAS.csv"),
new ArquivoCanvas(ger.getDisciplina(), Arrays.asList("course_id,", "short_name,", "long_name,", "status\n"), "DISCIPLINAS.csv")));
InputStream x = zipFiles(ger.getArquivos());
Thread.sleep(5000);
sendFileFTP(x, 1);
sendEmail("Arquivo gerado com sucesso! \r\n Nome do aquivo:" + fileName);
} catch (Exception e) {
System.out.println("######### FTP-CANVAS ########### ERRO: " + e.getMessage());
sendEmail("Algo deu errado! \r\n MOTIVO: \r\n " + e.getMessage());
}
}
}

private void sendEmail(String mensagem) {
try {
EmailModel emailModel = new EmailModel();
emailModel.setAssunto("Arquivos Canvas " + now);
emailModel.setDestinatarioEmail("fabio.silva@cruzeirodosul.edu.br");
emailModel.setDestinatarioCC("canvas716@cruzeirodosul.edu.br");
emailModel.setRemetenteEmail("sistemas@cruzeirodosul.edu.br");
emailModel.setRemetenteNome("Sistema Integrado de Administra��o Acad�mica - Scheduler");
emailModel.setTipo(2);
emailModel.setMenssagem(mensagem);
Email.sendMail(emailModel);
} catch (Exception e) {
System.out.println("########### Erro ao enviar o email! " + e.getMessage());
e.printStackTrace();
}
}

private void sendFileFTP(InputStream file, int attemps) throws IOException, InterruptedException {
String host = "200.136.79.5", user = "canvas", password = "canvas2019#$!";
try {
FTPClient ftp = connectFTP(host, user, password);
fileName = "CANVAS_" + now + ".zip";
String path = "arquivos/" + fileName;

ftp.storeFile(path, file);
Thread.sleep(5000);

FTPFile[] remoteFile = ftp.listFiles(path);
if (remoteFile.length > 0) {
ftp.logout();
ftp.disconnect();
} else {
//Nova tentativa
if (attemps < 2) {
sendFileFTP(file, 2);
}
System.out.println("######### FTP-CANVAS ########### ARQUIVO N�O ENCONTRADO | TENTATIVA: " + attemps);
}

} catch (IOException ex) {
System.out.println("######### FTP-CANVAS ########### ERRO: " + ex.getMessage());

sendEmail("Algo deu errado! \r\n MOTIVO: \r\n " + ex.getMessage());
}
}

private FTPClient connectFTP(String host, String user, String password) throws IOException {
FTPClient ftp = new FTPClient();
ftp.connect(host);
ftp.login(user, password);
ftp.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
return ftp;
}

private byte[] genCsvBytes(List<String[]> dados, List<String> cabecalho) {
ByteArrayOutputStream baos = new ByteArrayOutputStream();
String quebraLinha = "\r\n";
try {
for (String c : cabecalho) {
baos.write((c).getBytes());
}
for (int i = 0; i < dados.size(); i++) {
String[] linha = dados.get(i);
for (int j = 0; j < linha.length; j++) {
byte[] x = linha[j] == null ? ("").getBytes() : linha[j].getBytes();
baos.write(x);
if (j < linha.length - 1) {
baos.write(',');
}
}
baos.write(quebraLinha.getBytes());
}
baos.flush();
baos.close();
} catch (IOException ex) {
System.out.println("Erro ao gerar os bytes " + ex.getMessage());
ex.getStackTrace();
}
return baos.toByteArray();
}

private InputStream zipFiles(List<ArquivoCanvas> files) throws IOException {
ByteArrayOutputStream out = new ByteArrayOutputStream();
ZipOutputStream zos = new ZipOutputStream(out, StandardCharsets.UTF_8);
Charset charset = Charset.forName("utf-8");
try {
for (int i = 0; i < files.size(); i++) {
String fileName = files.get(i).getNome();
ArquivoCanvas arquivo = files.get(i);
byte dados[] = genCsvBytes(arquivo.getDado(), arquivo.getCabecalho());
dados = charset.encode(new String(dados)).array();
ZipEntry zipEntry = new ZipEntry(fileName);
zipEntry.setSize(dados.length);
zos.putNextEntry(zipEntry);
zos.write(dados);
zos.closeEntry();
}
zos.flush();
zos.close();

} catch (IOException e) {
System.out.println("######### FTP-CANVAS ########### ERRO: " + e.getMessage());
e.getStackTrace();
}

return new ByteArrayInputStream(out.toByteArray());
}

}
