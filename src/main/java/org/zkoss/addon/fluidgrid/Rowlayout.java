package org.zkoss.addon.fluidgrid;

import java.io.IOException;

import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.sys.ContentRenderer;
import org.zkoss.zul.impl.XulElement;

/**
 * Divide the parent container into a row of equal-width columns separated by spacings.
 * Stacking multiple rowlayout components with the same configuration creates a grid inside
 * the parent container. 
 * 
 * @author Neil
 * @see Rowchildren
 */
@SuppressWarnings("serial")
public class Rowlayout extends XulElement {
	// Default grid: 12 columns, each column = 60px, column spacing = 20px
	// resulting in a grid with width = 12*60px + 11*20px = 940px
	public static int DEFAULT_NUM_COLUMNS = 12;
	// Spacing should be given in ratio to the column width
	// e.g. set spacing = 0.5 so that spacing is half as wide as the column
	public static String DEFAULT_SPACING  = "0.333333333333333";
	
	private int _ncols      = DEFAULT_NUM_COLUMNS;
	private String _spacing = DEFAULT_SPACING;
	
	/**
	 * <p>Default: 12
	 * @return Number of columns to divide the row into
	 */
	public int getNcols() {
		return _ncols;
	}
	public void setNcols(int ncols) {
		if (ncols < 1) ncols = 1;
		if (ncols != _ncols) {
			_ncols = ncols;
			smartUpdate("ncols", ncols);
		}
	}
	
	/**
	 * Spacing between columns should be given as a ratio to the column width.
	 * e.g. "1/3", "33.3%", or "0.3333"
	 * <p>Default: 20.0/60.0 = 0.3333...
	 * @return Spacing between columns
	 */
	public String getSpacing() {
		return _spacing;
	}
	public void setSpacing(String value) {
		double spacing = 0.0;
		int slash = -1;
		
		// spacing can be assigned as 1. a ratio; 2. a percentage; OR 3. a real number
		try {
    		if (value.endsWith("%")) {
    			spacing = Double.parseDouble(value.replace("%", "")) / 100.0;
    		} else if ((slash = value.indexOf('/')) > 0) {
				spacing = 
					Integer.parseInt(value.substring(0,slash)) / 
					(double) Integer.parseInt(value.substring(slash+1));
    		} else {
				spacing = Double.parseDouble(value);
    		}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(value + " cannot be accepted as spacing");
		}
		if (spacing < 0) spacing = 0;
		if (!Objects.equals(spacing, Double.valueOf(_spacing))) {
			_spacing = String.valueOf(spacing);
			smartUpdate("spacing", spacing);
		}
	}
	
	@Override
	public void setWidth(String width) {
		throw new UnsupportedOperationException("readonly");
	}
	@Override
	public void setHflex(String hflex) {
		throw new UnsupportedOperationException("readonly");
	}
	
	@Override
	protected void renderProperties(ContentRenderer renderer) 
		throws IOException {
		super.renderProperties(renderer);
		
		if (_ncols    != DEFAULT_NUM_COLUMNS) render(renderer, "ncols", _ncols);
		if (_spacing  != DEFAULT_SPACING)     render(renderer, "spacing", _spacing);
	}
	
	@Override
	public void beforeChildAdded(Component child, Component refChild) {
		if (!(child instanceof Rowchildren))
			throw new UiException("Unsupported child for Rowlayout: " + child);
		super.beforeChildAdded(child, refChild);
	}

}
