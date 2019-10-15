package br.edu.cruzeirodosul.webapps.canvas.bean;

import br.edu.cruzeirodosul.models.academico.canvas.UsuarioCanvas;
import br.edu.cruzeirodosul.models.academico.canvas.UsuarioOfertaCanvas;
import br.edu.cruzeirodosul.models.academico.canvas.ArquivoCanvas;
import br.edu.cruzeirodosul.models.academico.canvas.DisciplinaCanvas;
import br.edu.cruzeirodosul.models.academico.canvas.OfertaCanvas;
import br.edu.cruzeirodosul.models.academico.canvas.UsuarioDadosCanvas;
import java.util.LinkedList;
import java.util.List;

/**
*
* @author lgeovanni
*/
public class GerarArquivoBean {

private List<String[]> aluno;
private List<String[]> alunoDados;
private List<String[]> alunoCriptofrafia;
private List<String[]> alunoOferta;
private List<String[]> oferta;
private List<String[]> disciplina;
private List<String[]> professor;
private List<String[]> professorOferta;

private List<ArquivoCanvas> arquivos;

public GerarArquivoBean(List<UsuarioCanvas> aluno, List<UsuarioOfertaCanvas> alunoOferta, List<UsuarioCanvas> professor, List<UsuarioOfertaCanvas> professorOferta, List<OfertaCanvas> oferta, List<DisciplinaCanvas> disciplina, List<UsuarioDadosCanvas> usuarioDados) {
this.aluno = montarUsuario(aluno, false);
this.professor = montarUsuario(professor, false);
this.alunoCriptofrafia = montarUsuario(aluno, true);
this.alunoOferta = montarUsuarioOferta(alunoOferta);
this.professorOferta = montarUsuarioOferta(professorOferta);
this.oferta = montarOferta(oferta);
this.disciplina = montarDisciplina(disciplina);
this.alunoDados = montarUsuarioDados(usuarioDados);
}

private List<String[]> montarUsuario(List<UsuarioCanvas> usuario, boolean cript) {
List<String[]> dados = new LinkedList<String[]>();
for (UsuarioCanvas x : usuario) {
String password = cript ? CriptografarCanvas.SHA(x.getPassword()) : x.getPassword();
dados.add(new String[]{
x.getUserId(),
x.getLoginId(),
password,
x.getFirstName(),
x.getLastName(),
x.getShortName(),
x.getEmail(),
x.getStatus()
});
}
return dados;
}

private List<String[]> montarUsuarioOferta(List<UsuarioOfertaCanvas> usuarioOferta) {
List<String[]> dados = new LinkedList<String[]>();
for (UsuarioOfertaCanvas x : usuarioOferta) {
dados.add(new String[]{
x.getCourseId(),
x.getUserId(),
x.getRole(),
x.getSectionId(),
x.getStatus()
});
}
return dados;
}

private List<String[]> montarOferta(List<OfertaCanvas> oferta) {
List<String[]> dados = new LinkedList<String[]>();
for (OfertaCanvas x : oferta) {
dados.add(new String[]{
x.getSectionId(),
x.getCourseId(),
x.getName(),
x.getStatus()
});
}
return dados;
}

private List<String[]> montarDisciplina(List<DisciplinaCanvas> disciplina) {
List<String[]> dados = new LinkedList<String[]>();
for (DisciplinaCanvas x : disciplina) {
dados.add(new String[]{
x.getCourseId(),
x.getShortName(),
x.getLongName(),
x.getStatus()
});
}
return dados;
}

private List<String[]> montarUsuarioDados(List<UsuarioDadosCanvas> usuarioDados) {
List<String[]> dados = new LinkedList<String[]>();
for (UsuarioDadosCanvas x : usuarioDados) {
dados.add(new String[]{
x.getUserId(),
x.getRgmNumber(),
x.getPoloNumber(),
x.getCourseName(),
x.getDocNumber()
});
}
return dados;
}

public void setArquivos(List<ArquivoCanvas> arquivos) {
this.arquivos = arquivos;
}

public List<ArquivoCanvas> getArquivos() {
return arquivos;
}

public ArquivoCanvas montarArquivo(List<String[]> dados, List<String> cabecalho, String nome) {
return new ArquivoCanvas(dados, cabecalho, nome);
}

public List<String[]> getAluno() {
return aluno;
}

public List<String[]> getAlunoCriptofrafia() {
return alunoCriptofrafia;
}

public List<String[]> getAlunoOferta() {
return alunoOferta;
}

public List<String[]> getOferta() {
return oferta;
}

public List<String[]> getDisciplina() {
return disciplina;
}

public List<String[]> getAlunoDados() {
return alunoDados;
}

public List<String[]> getProfessorOferta() {
return professorOferta;
}

public List<String[]> getProfessor() {
return professor;
}

}
