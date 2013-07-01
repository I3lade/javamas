/*
 * ShowDocumentation.java
 *
 * Created on June 3, 2003, 3:46 PM
 */
package javamas.gui;

import java.io.File;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 *
 * @author  monet
 * @version
 */
public class ShowDocumentation extends JFrame {

	private JEditorPane html;

	/** Creates new ShowDocumentation */
	public ShowDocumentation() {
		super("JaMas Documentation");
		this.setSize(640, 480);
		try {
			File theFile = new File("./javadoc/index.html");
			html = new JEditorPane(theFile.toURL());
			html.setEditable(false);
			html.addHyperlinkListener(createHyperLinkListener());
			JScrollPane vp = new JScrollPane(html);
			this.getContentPane().add(vp);
		} catch (java.net.MalformedURLException ex) {
			System.out.println("Malformed URL: " + ex);
		} catch (IOException ex) {
			System.out.println("IOException: " + ex);
		} catch (Exception ex) {
			System.out.println("Erreur lors du chargement de la page d'aide" + ex);
		}
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * 
	 * @return
	 */
	public HyperlinkListener createHyperLinkListener() {
		return new HyperlinkListener() {

			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					if (e instanceof HTMLFrameHyperlinkEvent) {
						((HTMLDocument) html.getDocument()).processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent) e);
					} else {
						try {
							html.setPage(e.getURL());
						} catch (IOException ioe) {
							System.out.println("IOE: " + ioe);
						}
					}
				}
			}
		};
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new ShowDocumentation();
	}
}
