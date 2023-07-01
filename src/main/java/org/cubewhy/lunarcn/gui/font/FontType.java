package org.cubewhy.lunarcn.gui.font;

public enum FontType {
	
	TITLE (new CustomFontRenderer("title", 15)),
	TEXT (new CustomFontRenderer("", 11)),
	TEXT_BOLD (new CustomFontRenderer("text_bold", 9));
	
	private final CustomFontRenderer font;
	
	FontType(CustomFontRenderer font) {
		this.font = font;
	}
	
	public CustomFontRenderer getFont() {
		return font;
	}

}
