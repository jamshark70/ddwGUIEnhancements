
HJHObjectGui : ObjectGui {
		// like felix's but it saves the layout that was passed in
	var	<mainLayout, <layout, <iMadeLayout = false,
		<argBounds;

	*initClass {
		StartUp.add {
			GUI.skins.put(\dewdrop,
				(
					fontSpecs: 	["Helvetica", 12.0],
					fontColor: 	Color.black,
					background: 	Color.white,
					foreground:	Color.grey(0.95),
					onColor:		Color.new255(255, 250, 250),
					offColor:		Color.clear,
					gap:			4 @ 4,
					margin: 		2@2,
					buttonHeight:	17
				));
//			GUI.setSkin(\dewdrop);
		}
	}

	guify { arg lay,bounds,title;
		argBounds = bounds;	// some of my gui's need to know this
		if(lay.isNil,{
			mainLayout = lay = this.class.windowClass.new
				(title ?? { model.asString.copyRange(0,50) },
				bounds);
			iMadeLayout = true;	// now when I'm removed, I'll close the window too
		},{
			mainLayout = lay;	// should only pass in the FixedWidthMultiPageLayout
			lay = lay.asPageLayout(title,bounds);
		});
		// i am not really a view in the hierarchy
		lay.removeOnClose(this);
		^lay
	}

	remove {
		model.notNil.if({
			view.notClosed.if({
				view.remove;
				mainLayout.recursiveResize;
			});
			model.view = nil;
			model = nil;
			iMadeLayout.if({
				mainLayout.close;
			});
		});
	}
	
	*windowClass { ^ResizeFlowWindow }
}

HJHFixedWidthObjectGui : HJHObjectGui {
	*windowClass { ^ResizeHeightFlowWindow }
}
