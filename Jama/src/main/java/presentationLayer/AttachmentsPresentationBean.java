package presentationLayer;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import annotations.TransferObj;
import businessLayer.Attachment;
import businessLayer.Contract;

@Named("attachmentsPB")
@ConversationScoped
public class AttachmentsPresentationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Attachment selectedAttachment;

	@Inject
	@TransferObj
	private Contract contract;

	public AttachmentsPresentationBean() {
		
	}

	public Attachment getSelectedAttachment() {
		return selectedAttachment;
	}

	public void setSelectedAttachment(Attachment selectedAttachment) {
		this.selectedAttachment = selectedAttachment;
	}

	public void handleFileUpload(FileUploadEvent event) {
		
		System.out.println("HANDLE UPLOAD ==============");

		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		UploadedFile file = event.getFile();
		System.out.println("upload di : ");
		System.out.println(file.getFileName());

		Attachment a = new Attachment();
		a.setContent(file.getContents());
		a.setFileName(file.getFileName());
		a.setFileType(file.getContentType());

		contract.getAttachments().add(a);

	}

	public List<Attachment> getAttachments() {


		return contract.getAttachments();

	}

	public StreamedContent getFile(Attachment a) {

		ByteArrayInputStream stream = new ByteArrayInputStream(a.getContent());
		StreamedContent file = new DefaultStreamedContent(stream,
				a.getFileType(), a.getFileName());
		return file;

	}

	public void removeFile() {
		

		contract.getAttachments().remove(selectedAttachment);

	}

}
