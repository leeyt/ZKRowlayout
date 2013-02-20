var Rowchildren =
fluidgrid.Rowchildren = zk.$extends(zul.Widget, {
	_colspan: 1,
	_offset: 0,
	
	$define: {
		colspan: [
			function(v) {
				return (v === undefined) ? 1 : (v < 1 ? 1 : v);
			},
			function() {
				var n, cls;
				if (this.desktop) {
					n = this.$n();
					cls = n.className;
					n.className = cls.replace(/colspan\d+/, '') + ' colspan' + this._colspan;
					this.parent._fixChild(this);
				}
			}
		],
		offset: [
			function(v) {
				return (v === undefined) ? 0 : (v < 0 ? 0 : v);
			},
			function() {
				var n, cls, offset;
				
				if (this.desktop) {
					n = this.$n();
					cls = n.className;
					n.className = 
						cls.replace(/offset\d+/, '') + 
						(((offset = this._offset) && offset > 0) ? ' offset' + offset : '');
					this.parent._fixChild(this);
				}
			}
		]
	},
	domClass_: function(no) {
		var cls = this.$supers('domClass_', arguments),
		    colspan = this._colspan, 
		    offset  = this._offset;
		cls += ' colspan' + colspan;
		cls += (offset && offset > 0) ? ' offset' + offset : '';
		return cls;
	},
	bind_: function() {
		this.$supers('bind_', arguments);
		this.parent._fixChild(this);
	}
});
