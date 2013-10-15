package presentationLayer;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import annotations.TransferObj;
import businessLayer.Agreement;
import businessLayer.Attachment;

@Named("attachmentsPB")
@ConversationScoped
public class AttachmentsPresentationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject @TransferObj
	private Agreement agreement;

	public AttachmentsPresentationBean() {
	}
	
	public void handleFileUpload(FileUploadEvent event){
		
		   FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
	        FacesContext.getCurrentInstance().addMessage(null, msg); 
		
		UploadedFile file = event.getFile();
		System.out.println("upload di : ");
		System.out.println(file.getFileName());
	
		Attachment a = new Attachment();
		a.setContent(file.getContents());
		a.setFileName(file.getFileName());
		a.setFileType(file.getContentType());
		
		agreement.getAttachments().add(a);
		
	}
	
	public List<Attachment> getAttachments(){
		
		return agreement.getAttachments();
		
	}

}
