package org.zkoss.addon.fluidgrid;

import java.io.IOException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.sys.ContentRenderer;
import org.zkoss.zul.impl.XulElement;

/**
 * Rowchildren component is used for placing components inside the grid created by rowlayout component.
 * It allows the user to specify the number of grid columns to occupy and whether to skip ahead a few
 * columns.
 * 
 * @author Neil
 * @see Rowlayout
 */
@SuppressWarnings("serial")
public class Rowchildren extends XulElement {

	private int _colspan = 1;
	private int _offset = 0;

	/**
	 * Number of columns to occupy 
	 */
	public int getColspan() {
		return _colspan;
	}
	public void setColspan(int colspan) {
		if (colspan < 0) colspan = 1;
		if (colspan != _colspan) {
			_colspan = colspan;
			this.smartUpdate("colspan", _colspan);
		}
	}
	
	/**
	 * Number of columns to skip ahead
	 * @return
	 */
	public int getOffset() {
		return _offset;
	}
	public void setOffset(int offset) {
		if (offset < 0) offset = 0;
		if (offset != _offset) {
			_offset = offset;
			smartUpdate("offset", _offset);
		}
	}
	
	@Override
	protected void renderProperties(ContentRenderer renderer) 
		throws IOException {
		super.renderProperties(renderer);
		
		if (_colspan != 1) render(renderer, "colspan", _colspan);
		if (_offset != 0)  render(renderer, "offset", _offset);
	}
	
	@Override
	public void beforeParentChanged(Component parent) {
		if (parent != null && !(parent instanceof Rowlayout))
			throw new UiException(
				"Wrong parent for " + this.getClass().getName()	+ ": " + parent);
		super.beforeParentChanged(parent);
	}

	@Override
	public void beforeChildAdded(Component child, Component insertBefore) {
		if (getChildren().size() > 0)
			throw new UiException("Only one child is allowed: " + this);
	
		super.beforeChildAdded(child, insertBefore);
	}
	
}
