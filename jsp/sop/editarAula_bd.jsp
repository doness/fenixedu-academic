<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<table width="100%" cellspacing="0">
	<tr>
    	<td class="infoselected"><p>O curso seleccionado &eacute;:</p>
			<strong><jsp:include page="context.jsp"/></strong>
       	</td>
  	</tr>
</table>
<br />
<h2><bean:message key="title.editAula"/></h2>
<br />
<span class="error"><html:errors/></span>
	<html:form action="/editarAulaForm">
	
<table> 
	<tr>
    	<td class="formTD"><bean:message key="property.aula.weekDay"/>: </td>
      	<td class="formTD"><html:text property="diaSemana"  size="1"/>
      	</td>
   	</tr>
    <tr>
     	<td class="formTD"><bean:message key="property.aula.time.begining"/>: </td>
       	<td class="formTD"><html:text property="horaInicio"  size="1"/>
            	 :
            <html:text property="minutosInicio"  size="1"/>
            
  		</td>
  	</tr>
    <tr>
       	<td class="formTD"><bean:message key="property.aula.time.end"/>: </td>
       	<td class="formTD"><html:text property="horaFim"  size="1"/>
            	:
          	<html:text property="minutosFim"  size="1"/>
            	
       	</td>
  	</tr>
    <tr>
    	<td class="formTD"><bean:message key="property.aula.type"/>: </td>
        <td class="formTD"><html:select property="tipoAula">
            	<html:options collection="tiposAula" property="value" labelProperty="label"/>
            </html:select>
        </td>
  	</tr>
    <tr>
		<td class="formTD"><bean:message key="property.aula.sala"/>: </td>
    	<td class="formTD"><span class="grey-txt"><b><html:hidden property="nomeSala" write="true"/></b></span>
    </tr>
</table>
<br />
<html:submit property="operation" styleClass="inputbutton"><bean:message key="lable.changeRoom"/></html:submit>
<html:submit property="operation" styleClass="inputbutton"><bean:message key="label.save"/></html:submit>
<html:reset styleClass="inputbutton"><bean:message key="label.clear"/></html:reset>
</html:form>