package com.eastedu.render.style;

import com.eastedu.render.style.decorator.CompositeDecoratorRender;

/**
 * The type Style manager.
 *
 * @author superman
 */
public class StyleManager {
    private final CompositeDecoratorRender underlineRender = new CompositeDecoratorRender();
    private final FontStyleRender fontStyleRenderer = new FontStyleRender();
}
