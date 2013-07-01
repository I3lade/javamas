package javamas.gui.editor;

import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.text.*;

/**
 *
 * @author Guillaume Monet
 */
public class JavaDocument extends DefaultStyledDocument {

	private String[] keyWords = new String[]{"this", "class", "public", "private", "try", "catch", "if", "else", "for", "do", "while", "break", "float", "int ", "boolean ", "double ", "char ", "implements", "extends", "interface", "protected", "synchronized", "throws", "true", "false", "new", "static", "import", "package", "static", "void", "super", "enum","volatile"};
	private JEditorPane pane = new JEditorPane();

	public JavaDocument(JEditorPane pane) {
		this.pane = pane;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
		super.insertString(offset, str, a);
		apply(0, getLength());
	}

	@Override
	public void remove(int offset, int length) throws BadLocationException {
		super.remove(offset, length);
		apply(0, getLength());
	}

	public boolean apply(int startOffset, int endOffset) throws BadLocationException {
		if (startOffset >= endOffset) {
			return false;
		}
		boolean result = false;
		DefaultStyledDocument doc = (DefaultStyledDocument) pane.getDocument();
		MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setForeground(attr, Color.black);
		doc.setCharacterAttributes(startOffset, endOffset - startOffset, attr, true);

		String content = doc.getText(0, doc.getLength());

		content = content.toLowerCase();
		//---------COMMENTS-------------------------------
		int commentIndex = content.indexOf("/*", startOffset);
		int quoteIndex = content.indexOf("'", startOffset);
		int doubleQuoteIndex = content.indexOf("\"", startOffset);

		if (commentIndex == -1) {
			commentIndex = endOffset + 1;
		}
		if (quoteIndex == -1) {
			quoteIndex = endOffset + 1;
		}
		if (doubleQuoteIndex == -1) {
			doubleQuoteIndex = endOffset + 1;
		}

		if ((commentIndex > quoteIndex) || (commentIndex > doubleQuoteIndex)) {
			commentIndex = endOffset + 1;
		}
		if ((quoteIndex > commentIndex) || (quoteIndex > doubleQuoteIndex)) {
			quoteIndex = endOffset + 1;
		}
		if ((doubleQuoteIndex > quoteIndex) || (doubleQuoteIndex > commentIndex)) {
			doubleQuoteIndex = endOffset + 1;
		}


		if ((commentIndex <= endOffset)) {
			apply(startOffset, commentIndex - 1);
			int endCommentIndex = content.indexOf("*/", commentIndex + 1);
			if (endCommentIndex > -1) {
				endCommentIndex = Math.min(endOffset, endCommentIndex + 2);
			} else {
				endCommentIndex = endOffset;
			}
			MutableAttributeSet attr1 = new SimpleAttributeSet();
			StyleConstants.setForeground(attr1, Color.gray);
			StyleConstants.setItalic(attr1, true);
			doc.setCharacterAttributes(commentIndex, endCommentIndex - commentIndex, attr1, false);
			apply(endCommentIndex, endOffset);
			result = true;
		}
		//------SINGLE QUOTE----------------------------------
		Color singleQuotedStringColor = Color.green;
		if ((quoteIndex <= endOffset)) {
			apply(startOffset, quoteIndex - 1);
			int endQuoteIndex = content.indexOf("'", quoteIndex + 1);
			if (endQuoteIndex > -1) {
				endQuoteIndex = Math.min(endOffset, endQuoteIndex + 1);
			} else {
				endQuoteIndex = endOffset;
			}
			MutableAttributeSet attr1 = new SimpleAttributeSet();
			StyleConstants.setForeground(attr1, singleQuotedStringColor);
			StyleConstants.setItalic(attr1, false);
			doc.setCharacterAttributes(quoteIndex, endQuoteIndex - quoteIndex, attr1, false);
			apply(endQuoteIndex, endOffset);
			result = true;
		}
		//------DOUBLE QUOTE----------------------------------
		Color doubleQuotedStringColor = Color.pink;
		if ((doubleQuoteIndex <= endOffset)) {
			apply(startOffset, doubleQuoteIndex - 1);
			int endQuoteIndex = content.indexOf("\"", doubleQuoteIndex + 1);
			if (endQuoteIndex > -1) {
				endQuoteIndex = Math.min(endOffset, endQuoteIndex + 1);
			} else {
				endQuoteIndex = endOffset;
			}
			MutableAttributeSet attr1 = new SimpleAttributeSet();
			StyleConstants.setForeground(attr1, doubleQuotedStringColor);
			StyleConstants.setItalic(attr1, false);
			doc.setCharacterAttributes(doubleQuoteIndex, endQuoteIndex - doubleQuoteIndex, attr1, false);
			apply(endQuoteIndex, endOffset);
			result = true;
		}
		//----------------------------------------
		if (result) {
			return true;
		}
		int cnt = keyWords.length;
		for (int i = 0; i < cnt; i++) {
			int offset = startOffset;
			int index = content.indexOf(keyWords[i], offset);
			while ((index > -1) && (index <= endOffset)) {
				attr = new SimpleAttributeSet();
				StyleConstants.setForeground(attr, Color.blue);
				StyleConstants.setBold(attr, true);
				int len = keyWords[i].length();
				doc.setCharacterAttributes(index, len, attr, false);
				result = true;
				offset = index + 1;
				index = content.indexOf(keyWords[i], offset);
			}
		}
		return result;
	}
}
