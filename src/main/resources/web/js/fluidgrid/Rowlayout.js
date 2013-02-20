var Rowlayout = 
fluidgrid.Rowlayout = zk.$extends(zul.Widget, {
	// Logically, the parent container is divided into equal-width columns with spacing in between.
	// Together, the number of columns and their separation define the layout of the row
	_ncols:    12,
	_spacing:  20/60,
	
	// Sizing parameters 
	_rowWidth:  15 + 2/3,
	_pSpacing:  1/47,
	_pColWidth: 3/47,
	
	$define: {
		ncols: [
			function(v) {
				return (v === undefined) ? 12 : (v < 1 ? 1 : v);
			},
			function() {
				this._syncSizingParam();
				if (this.desktop) this._fixLayout();
			}
		],
		
		spacing: [
			function(v) {
				return (v === undefined) ? (20/60) : (v < 0 ? 0 : v);
			},
			function() {
				this._syncSizingParam();
				if (this.desktop) this._fixLayout();
			}
		],		
	},

	_oldwth: 0,
	_oldhgh: 0,

	_fixLayout: function() {
		for (var w = this.firstChild; w; w = w.nextSibling) {
			if (w.desktop) this._fixChild(w);
		}

		/*
		var n = this.$n(), cs = getComputedStyle(n),
			newwth = cs.getPropertyValue('width'),
			newhgh = cs.getPropertyValue('height');
		
		if (newwth != this._oldwth || newhgh != this._oldhgh) {
			this._oldwth = newwth;
			this._oldhgh = newhgh;

			zUtl.fireSized(this.parent);		
		}
		*/
	},
	
	_syncSizingParam: function() {
		var ncols = this._ncols,
			spacing = this._spacing,
			rowWidth = this._rowWidth = ncols + (ncols-1)*spacing;
		
		this._pSpacing  = spacing / rowWidth;
		this._pColWidth = 1 / rowWidth;
	},
	
	_fixChild: function(child) {
		if (!child.desktop) return;
		var rowWidth  = this._rowWidth,
			pSpacing  = this._pSpacing,
			pColWidth = this._pColWidth;
		
		function colspanWidthRatio(col) {
			return (pColWidth+pSpacing)*col - pSpacing;
		}
		function offsetMarginRatio(col) {
			return (pColWidth+pSpacing)*col + pSpacing;
		}
		function percent(frac) {
			return (frac*100)+'%';
		}

		var n = child.$n(),
			cw = parseInt(getComputedStyle(this.parent.$n()).getPropertyValue('width'))*pColWidth;
		
		// Make sure column does not become too narrow
		if (cw >= 50) {
			var offset = child._offset,
				marginLeft = (offset > 0) ? offsetMarginRatio(offset) : pSpacing;
		
			jq(n)
				.css('width', percent(colspanWidthRatio(child._colspan)))
				.css('margin-left', 
					(child == child.parent.firstChild)
					? percent(marginLeft - pSpacing) 
					: percent(marginLeft));
		} else {
			jq(n)
				.css('width', '100%')
				.css('margin-left', 0);
		}
		
		// only fire when child has h/vflex
		for (var w = child.firstChild; w; w = w.nextSibling) {
			if (w._nvflex || w._nhflex) {
				zUtl.fireSized(w);
				break;
			}
		}
	},
	
	bind_: function() {
		this.$supers('bind_', arguments);
		zWatch.listen({onSize: this});
	},
	
	unbind_: function() {
		zWatch.unlisten({onSize: this});
		this.$supers('unbind_', arguments);
	},
	
	beforeParentMinFlex_: function(attr) {
		if (attr === 'h') {
			console.log('parent: ' + getComputedStyle(this.$n()).getPropertyValue('height'));
		}		
	},
	
	afterChildrenFixFlex_: function() {
		console.log('afterChildrenFixFlex_');
	},
	
	afterResetChildSize_: function(kid) {
		console.log('afterResetChildSize_');
	},
	
	onSize: function() {
		this._fixLayout();
	}
});
