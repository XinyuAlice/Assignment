/*
 * Copyright 2008-2013 Andre Pfeiler
 *
 * This file is part of FindBugs-IDEA.
 *
 * FindBugs-IDEA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FindBugs-IDEA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FindBugs-IDEA.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.twodividedbyzero.idea.findbugs.gui.common;

import javax.swing.UIManager;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;


/**
 * The MultiSplitLayout layout manager recursively arranges its
 * components in row and column groups called "Splits".  Elements of
 * the layout are separated by gaps called "Dividers".  The overall
 * layout is defined with a simple tree model whose nodes are
 * instances of MultiSplitLayout.Split, MultiSplitLayout.Divider,
 * and MultiSplitLayout.Leaf. Named Leaf nodes represent the space
 * allocated to a component that was added with a constraint that
 * matches the Leaf's name.  Extra space is distributed
 * among row/column siblings according to their 0.0 to 1.0 weight.
 * If no weights are specified then the last sibling always gets
 * all of the extra space, or space reduction.
 * <p/>
 * <p/>
 * Although MultiSplitLayout can be used with any Container, it's
 * the default layout manager for MultiSplitPane.  MultiSplitPane
 * supports interactively dragging the Dividers, accessibility,
 * and other features associated with split panes.
 * <p/>
 * <p/>
 * All properties in this class are bound: when a properties value
 * is changed, all PropertyChangeListeners are fired.
 *
 * @author Hans Muller
 * @author Andre Pfeiler - some minor adaptations
 * @see MultiSplitPane
 */

@SuppressWarnings({"HardCodedStringLiteral", "NestedAssignment"})
public class MultiSplitLayout implements LayoutManager {

	private final Map<String, Component> childMap = new HashMap<String, Component>();
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private transient Node model;
	private int dividerSize;
	private boolean floatingDividers = true;


	/**
	 * Create a MultiSplitLayout with a default model with a single
	 * Leaf node named "default".
	 * <p/>
	 * #see setModel
	 */
	public MultiSplitLayout() {
		this(new Leaf("default"));
	}


	/**
	 * Create a MultiSplitLayout with the specified model.
	 * <p/>
	 * #see setModel
	 * @param model
	 */
	private MultiSplitLayout(final Node model) {
		this.model = model;
		dividerSize = UIManager.getInt("SplitPane.dividerSize");
		if (dividerSize == 0) {
			dividerSize = 7;
		}
	}


	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		if (listener != null) {
			pcs.addPropertyChangeListener(listener);
		}
	}


	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		if (listener != null) {
			pcs.removePropertyChangeListener(listener);
		}
	}


	public PropertyChangeListener[] getPropertyChangeListeners() {
		return pcs.getPropertyChangeListeners();
	}


	private void firePCS(final String propertyName, final Object oldValue, final Object newValue) {
		if (!(oldValue != null && newValue != null && oldValue.equals(newValue))) {
			pcs.firePropertyChange(propertyName, oldValue, newValue);
		}
	}


	/**
	 * Return the root of the tree of Split, Leaf, and Divider nodes
	 * that define this layout.
	 *
	 * @return the value of the model property
	 * @see #setModel
	 */
	Node getModel() {
		return model;
	}


	/**
	 * Set the root of the tree of Split, Leaf, and Divider nodes
	 * that define this layout.  The model can be a Split node
	 * (the typical case) or a Leaf.  The default value of this
	 * property is a Leaf named "default".
	 *
	 * @param model the root of the tree of Split, Leaf, and Divider node
	 * @throws IllegalArgumentException if model is a Divider or null
	 * @see #getModel
	 */
	public void setModel(final Node model) {
		if (model == null || model instanceof Divider) {
			throw new IllegalArgumentException("invalid model");
		}
		this.model = model;
		firePCS("model", model, model);
	}


	/**
	 * Returns the width of Dividers in Split rows, and the height of
	 * Dividers in Split columns.
	 *
	 * @return the value of the dividerSize property
	 * @see #setDividerSize
	 */
	int getDividerSize() {
		return dividerSize;
	}


	/**
	 * Sets the width of Dividers in Split rows, and the height of
	 * Dividers in Split columns.  The default value of this property
	 * is the same as for JSplitPane Dividers.
	 *
	 * @param dividerSize the size of dividers (pixels)
	 * @throws IllegalArgumentException if dividerSize < 0
	 * @see #getDividerSize
	 */
	public void setDividerSize(final int dividerSize) {
		if (dividerSize < 0) {
			throw new IllegalArgumentException("invalid dividerSize");
		}
		final int oldDividerSize = this.dividerSize;
		this.dividerSize = dividerSize;
		firePCS("dividerSize", oldDividerSize, dividerSize);
	}


	/**
	 * @return the value of the floatingDividers property
	 * @see #setFloatingDividers
	 */
	public boolean getFloatingDividers() {
		return floatingDividers;
	}


	/**
	 * If true, Leaf node bounds match the corresponding component's
	 * preferred size and Splits/Dividers are resized accordingly.
	 * If false then the Dividers define the bounds of the adjacent
	 * Split and Leaf nodes.  Typically this property is set to false
	 * after the (MultiSplitPane) user has dragged a Divider.
	 *
	 * @param floatingDividers
	 * @see #getFloatingDividers
	 */
	public void setFloatingDividers(final boolean floatingDividers) {
		final boolean oldFloatingDividers = this.floatingDividers;
		this.floatingDividers = floatingDividers;
		firePCS("floatingDividers", oldFloatingDividers, floatingDividers);
	}


	/**
	 * Add a component to this MultiSplitLayout.  The
	 * <code>name</code> should match the name property of the Leaf
	 * node that represents the bounds of <code>child</code>.  After
	 * layoutContainer() recomputes the bounds of all of the nodes in
	 * the model, it will set this child's bounds to the bounds of the
	 * Leaf node with <code>name</code>.  Note: if a component was already
	 * added with the same name, this method does not remove it from
	 * its parent.
	 *
	 * @param name  identifies the Leaf node that defines the child's bounds
	 * @param child the component to be added
	 * @see #removeLayoutComponent
	 */
	public void addLayoutComponent(final String name, final Component child) {
		if (name == null) {
			throw new IllegalArgumentException("name not specified");
		}
		childMap.put(name, child);
	}


	/**
	 * Removes the specified component from the layout.
	 *
	 * @param child the component to be removed
	 * @see #addLayoutComponent
	 */
	public void removeLayoutComponent(final Component child) {
		final String name = child.getName();
		if (name != null) {
			childMap.remove(name);
		}
	}


	private Component childForNode(final Node node) {
		if (node instanceof Leaf) {
			final Leaf leaf = (Leaf) node;
			final String name = leaf.getName();
			return name != null ? childMap.get(name) : null;
		}
		return null;
	}


	private Dimension preferredComponentSize(final Node node) {
		final Component child = childForNode(node);
		return child != null ? child.getPreferredSize() : new Dimension(0, 0);

	}


	private Dimension preferredNodeSize(final Node root) {
		if (root instanceof Leaf) {
			return preferredComponentSize(root);
		} else if (root instanceof Divider) {
			final int dividerSize = getDividerSize();
			return new Dimension(dividerSize, dividerSize);
		} else {
			final Split split = (Split) root;
			final List<Node> splitChildren = split.getChildren();
			int width = 0;
			int height = 0;
			if (split.isRowLayout()) {
				for (final Node splitChild : splitChildren) {
					final Dimension size = preferredNodeSize(splitChild);
					width += size.width;
					height = Math.max(height, size.height);
				}
			} else {
				for (final Node splitChild : splitChildren) {
					final Dimension size = preferredNodeSize(splitChild);
					width = Math.max(width, size.width);
					height += size.height;
				}
			}
			return new Dimension(width, height);
		}
	}


	private Dimension minimumNodeSize(final Node root) {
		if (root instanceof Leaf) {
			final Component child = childForNode(root);
			return child != null ? child.getMinimumSize() : new Dimension(0, 0);
		} else if (root instanceof Divider) {
			final int dividerSize = getDividerSize();
			return new Dimension(dividerSize, dividerSize);
		} else {
			final Split split = (Split) root;
			final List<Node> splitChildren = split.getChildren();
			int width = 0;
			int height = 0;
			if (split.isRowLayout()) {
				for (final Node splitChild : splitChildren) {
					final Dimension size = minimumNodeSize(splitChild);
					width += size.width;
					height = Math.max(height, size.height);
				}
			} else {
				for (final Node splitChild : splitChildren) {
					final Dimension size = minimumNodeSize(splitChild);
					width = Math.max(width, size.width);
					height += size.height;
				}
			}
			return new Dimension(width, height);
		}
	}


	private static Dimension sizeWithInsets(final Container parent, final Dimension size) {
		final Insets insets = parent.getInsets();
		final int width = size.width + insets.left + insets.right;
		final int height = size.height + insets.top + insets.bottom;
		return new Dimension(width, height);
	}


	public Dimension preferredLayoutSize(final Container parent) {
		final Dimension size = preferredNodeSize(getModel());
		return sizeWithInsets(parent, size);
	}


	public Dimension minimumLayoutSize(final Container parent) {
		final Dimension size = minimumNodeSize(getModel());
		return sizeWithInsets(parent, size);
	}


	private static Rectangle boundsWithYandHeight(final Rectangle bounds, final double y, final double height) {
		final Rectangle r = new Rectangle();
		r.setBounds((int) bounds.getX(), (int) y, (int) bounds.getWidth(), (int) height);
		return r;
	}


	private static Rectangle boundsWithXandWidth(final Rectangle bounds, final double x, final double width) {
		final Rectangle r = new Rectangle();
		r.setBounds((int) x, (int) bounds.getY(), (int) width, (int) bounds.getHeight());
		return r;
	}


	private static void minimizeSplitBounds(final Split split, final Rectangle bounds) {
		final Rectangle splitBounds = new Rectangle(bounds.x, bounds.y, 0, 0);
		final List<Node> splitChildren = split.getChildren();
		final Node lastChild = splitChildren.get(splitChildren.size() - 1);
		final Rectangle lastChildBounds = lastChild.getBounds();
		if (split.isRowLayout()) {
			final int lastChildMaxX = lastChildBounds.x + lastChildBounds.width;
			splitBounds.add(lastChildMaxX, bounds.y + bounds.height);
		} else {
			final int lastChildMaxY = lastChildBounds.y + lastChildBounds.height;
			splitBounds.add(bounds.x + bounds.width, lastChildMaxY);
		}
		split.setBounds(splitBounds);
	}


	private void layoutShrink(final Split split, final Rectangle bounds) {
		final Rectangle splitBounds = split.getBounds();
		final ListIterator<Node> splitChildren = split.getChildren().listIterator();
		//final Node lastWeightedChild = split.lastWeightedChild();

		if (split.isRowLayout()) {
			int totalWidth = 0;		  // sum of the children's widths
			int minWeightedWidth = 0;	// sum of the weighted childrens' min widths
			int totalWeightedWidth = 0;  // sum of the weighted childrens' widths
			for (final Node splitChild : split.getChildren()) {
				final int nodeWidth = splitChild.getBounds().width;
				final int nodeMinWidth = Math.min(nodeWidth, minimumNodeSize(splitChild).width);
				totalWidth += nodeWidth;
				if (splitChild.getWeight() > 0.0) {
					minWeightedWidth += nodeMinWidth;
					totalWeightedWidth += nodeWidth;
				}
			}

			double x = bounds.getX();
			final double extraWidth = splitBounds.getWidth() - bounds.getWidth();
			double availableWidth = extraWidth;
			final boolean onlyShrinkWeightedComponents = totalWeightedWidth - minWeightedWidth > extraWidth;

			while (splitChildren.hasNext()) {
				final Node splitChild = splitChildren.next();
				final Rectangle splitChildBounds = splitChild.getBounds();
				final double minSplitChildWidth = minimumNodeSize(splitChild).getWidth();
				final double splitChildWeight = onlyShrinkWeightedComponents ? splitChild.getWeight() : splitChildBounds.getWidth() / (double) totalWidth;

				if (!splitChildren.hasNext()) {
					final double newWidth = Math.max(minSplitChildWidth, bounds.getMaxX() - x);
					final Rectangle newSplitChildBounds = boundsWithXandWidth(bounds, x, newWidth);
					layout2(splitChild, newSplitChildBounds);
				} else if (availableWidth > 0.0 && splitChildWeight > 0.0) {
					final double allocatedWidth = Math.rint(splitChildWeight * extraWidth);
					final double oldWidth = splitChildBounds.getWidth();
					final double newWidth = Math.max(minSplitChildWidth, oldWidth - allocatedWidth);
					final Rectangle newSplitChildBounds = boundsWithXandWidth(bounds, x, newWidth);
					layout2(splitChild, newSplitChildBounds);
					availableWidth -= oldWidth - splitChild.getBounds().getWidth();
				} else {
					final double existingWidth = splitChildBounds.getWidth();
					final Rectangle newSplitChildBounds = boundsWithXandWidth(bounds, x, existingWidth);
					layout2(splitChild, newSplitChildBounds);
				}
				x = splitChild.getBounds().getMaxX();
			}
		} else {
			int totalHeight = 0;		  // sum of the children's heights
			int minWeightedHeight = 0;	// sum of the weighted childrens' min heights
			int totalWeightedHeight = 0;  // sum of the weighted childrens' heights
			for (final Node splitChild : split.getChildren()) {
				final int nodeHeight = splitChild.getBounds().height;
				final int nodeMinHeight = Math.min(nodeHeight, minimumNodeSize(splitChild).height);
				totalHeight += nodeHeight;
				if (splitChild.getWeight() > 0.0) {
					minWeightedHeight += nodeMinHeight;
					totalWeightedHeight += nodeHeight;
				}
			}

			double y = bounds.getY();
			final double extraHeight = splitBounds.getHeight() - bounds.getHeight();
			double availableHeight = extraHeight;
			final boolean onlyShrinkWeightedComponents = totalWeightedHeight - minWeightedHeight > extraHeight;

			while (splitChildren.hasNext()) {
				final Node splitChild = splitChildren.next();
				final Rectangle splitChildBounds = splitChild.getBounds();
				final double minSplitChildHeight = minimumNodeSize(splitChild).getHeight();
				final double splitChildWeight = onlyShrinkWeightedComponents ? splitChild.getWeight() : splitChildBounds.getHeight() / (double) totalHeight;

				if (!splitChildren.hasNext()) {
					final double oldHeight = splitChildBounds.getHeight();
					final double newHeight = Math.max(minSplitChildHeight, bounds.getMaxY() - y);
					final Rectangle newSplitChildBounds = boundsWithYandHeight(bounds, y, newHeight);
					layout2(splitChild, newSplitChildBounds);
					availableHeight -= oldHeight - splitChild.getBounds().getHeight();
				} else if (availableHeight > 0.0 && splitChildWeight > 0.0) {
					final double allocatedHeight = Math.rint(splitChildWeight * extraHeight);
					final double oldHeight = splitChildBounds.getHeight();
					final double newHeight = Math.max(minSplitChildHeight, oldHeight - allocatedHeight);
					final Rectangle newSplitChildBounds = boundsWithYandHeight(bounds, y, newHeight);
					layout2(splitChild, newSplitChildBounds);
					availableHeight -= oldHeight - splitChild.getBounds().getHeight();
				} else {
					final double existingHeight = splitChildBounds.getHeight();
					final Rectangle newSplitChildBounds = boundsWithYandHeight(bounds, y, existingHeight);
					layout2(splitChild, newSplitChildBounds);
				}
				y = splitChild.getBounds().getMaxY();
			}
		}

		/* The bounds of the Split node root are set to be
		 * big enough to contain all of its children. Since
		 * Leaf children can't be reduced below their
		 * (corresponding java.awt.Component) minimum sizes,
		 * the size of the Split's bounds maybe be larger than
		 * the bounds we were asked to fit within.
		 */
		minimizeSplitBounds(split, bounds);
	}


	private void layoutGrow(final Split split, final Rectangle bounds) {
		final Rectangle splitBounds = split.getBounds();
		final ListIterator<Node> splitChildren = split.getChildren().listIterator();
		final Node lastWeightedChild = split.lastWeightedChild();

		/* Layout the Split's child Nodes' along the X axis.  The bounds
			 * of each child will have the same y coordinate and height as the
			 * layoutGrow() bounds argument.  Extra width is allocated to the
			 * to each child with a non-zero weight:
			 *     newWidth = currentWidth + (extraWidth * splitChild.getWeight())
			 * Any extraWidth "left over" (that's availableWidth in the loop
			 * below) is given to the last child.  Note that Dividers always
			 * have a weight of zero, and they're never the last child.
			 */
		if (split.isRowLayout()) {
			double x = bounds.getX();
			final double extraWidth = bounds.getWidth() - splitBounds.getWidth();
			double availableWidth = extraWidth;

			while (splitChildren.hasNext()) {
				final Node splitChild = splitChildren.next();
				final Rectangle splitChildBounds = splitChild.getBounds();
				final double splitChildWeight = splitChild.getWeight();

				if (!splitChildren.hasNext()) {
					final double newWidth = bounds.getMaxX() - x;
					final Rectangle newSplitChildBounds = boundsWithXandWidth(bounds, x, newWidth);
					layout2(splitChild, newSplitChildBounds);
				} else if (availableWidth > 0.0 && splitChildWeight > 0.0) {
					final double allocatedWidth = splitChild.equals(lastWeightedChild) ? availableWidth : Math.rint(splitChildWeight * extraWidth);
					final double newWidth = splitChildBounds.getWidth() + allocatedWidth;
					final Rectangle newSplitChildBounds = boundsWithXandWidth(bounds, x, newWidth);
					layout2(splitChild, newSplitChildBounds);
					availableWidth -= allocatedWidth;
				} else {
					final double existingWidth = splitChildBounds.getWidth();
					final Rectangle newSplitChildBounds = boundsWithXandWidth(bounds, x, existingWidth);
					layout2(splitChild, newSplitChildBounds);
				}
				x = splitChild.getBounds().getMaxX();
			}
		}

		/* Layout the Split's child Nodes' along the Y axis.  The bounds
			 * of each child will have the same x coordinate and width as the
			 * layoutGrow() bounds argument.  Extra height is allocated to the
			 * to each child with a non-zero weight:
			 *     newHeight = currentHeight + (extraHeight * splitChild.getWeight())
			 * Any extraHeight "left over" (that's availableHeight in the loop
			 * below) is given to the last child.  Note that Dividers always
			 * have a weight of zero, and they're never the last child.
			 */
		else {
			double y = bounds.getY();
			final double extraHeight = bounds.getMaxY() - splitBounds.getHeight();
			double availableHeight = extraHeight;

			while (splitChildren.hasNext()) {
				final Node splitChild = splitChildren.next();
				final Rectangle splitChildBounds = splitChild.getBounds();
				final double splitChildWeight = splitChild.getWeight();

				if (!splitChildren.hasNext()) {
					final double newHeight = bounds.getMaxY() - y;
					final Rectangle newSplitChildBounds = boundsWithYandHeight(bounds, y, newHeight);
					layout2(splitChild, newSplitChildBounds);
				} else if (availableHeight > 0.0 && splitChildWeight > 0.0) {
					final double allocatedHeight = splitChild.equals(lastWeightedChild) ? availableHeight : Math.rint(splitChildWeight * extraHeight);
					final double newHeight = splitChildBounds.getHeight() + allocatedHeight;
					final Rectangle newSplitChildBounds = boundsWithYandHeight(bounds, y, newHeight);
					layout2(splitChild, newSplitChildBounds);
					availableHeight -= allocatedHeight;
				} else {
					final double existingHeight = splitChildBounds.getHeight();
					final Rectangle newSplitChildBounds = boundsWithYandHeight(bounds, y, existingHeight);
					layout2(splitChild, newSplitChildBounds);
				}
				y = splitChild.getBounds().getMaxY();
			}
		}
	}


	/* Second pass of the layout algorithm: branch to layoutGrow/Shrink
		 * as needed.
		 */
	private void layout2(final Node root, final Rectangle bounds) {
		if (root instanceof Leaf) {
			final Component child = childForNode(root);
			if (child != null) {
				child.setBounds(bounds);
			}
			root.setBounds(bounds);
		} else if (root instanceof Divider) {
			root.setBounds(bounds);
		} else if (root instanceof Split) {
			final Split split = (Split) root;
			final boolean grow = split.isRowLayout() ? split.getBounds().width <= bounds.width : split.getBounds().height <= bounds.height;
			if (grow) {
				layoutGrow(split, bounds);
				root.setBounds(bounds);
			} else {
				layoutShrink(split, bounds);
				// split.setBounds() called in layoutShrink()
			}
		}
	}


	/* First pass of the layout algorithm.
		 *
		 * If the Dividers are "floating" then set the bounds of each
		 * node to accomodate the preferred size of all of the
		 * Leaf's java.awt.Components.  Otherwise, just set the bounds
		 * of each Leaf/Split node so that it's to the left of (for
		 * Split.isRowLayout() Split children) or directly above
		 * the Divider that follows.
		 *
		 * This pass sets the bounds of each Node in the layout model.  It
		 * does not resize any of the parent Container's
		 * (java.awt.Component) children.  That's done in the second pass,
		 * see layoutGrow() and layoutShrink().
		 */
	private void layout1(final Node root, final Rectangle bounds) {
		if (root instanceof Leaf) {
			root.setBounds(bounds);
		} else if (root instanceof Split) {
			final Split split = (Split) root;
			final Iterator<Node> splitChildren = split.getChildren().iterator();
			Rectangle childBounds;
			final int dividerSize = getDividerSize();

			/* Layout the Split's child Nodes' along the X axis.  The bounds
					 * of each child will have the same y coordinate and height as the
					 * layout1() bounds argument.
					 *
					 * Note: the column layout code - that's the "else" clause below
					 * this if, is identical to the X axis (rowLayout) code below.
					 */
			if (split.isRowLayout()) {
				double x = bounds.getX();
				while (splitChildren.hasNext()) {
					final Node splitChild = splitChildren.next();
					final Divider dividerChild = splitChildren.hasNext() ? (Divider) splitChildren.next() : null;

					final double childWidth;
					if (getFloatingDividers()) {
						childWidth = preferredNodeSize(splitChild).getWidth();
					} else {
						if (dividerChild != null) {
							childWidth = dividerChild.getBounds().getX() - x;
						} else {
							childWidth = split.getBounds().getMaxX() - x;
						}
					}
					childBounds = boundsWithXandWidth(bounds, x, childWidth);
					layout1(splitChild, childBounds);

					if (getFloatingDividers() && dividerChild != null) {
						final double dividerX = childBounds.getMaxX();
						final Rectangle dividerBounds = boundsWithXandWidth(bounds, dividerX, dividerSize);
						dividerChild.setBounds(dividerBounds);
					}
					if (dividerChild != null) {
						x = dividerChild.getBounds().getMaxX();
					}
				}
			}

			/* Layout the Split's child Nodes' along the Y axis.  The bounds
					 * of each child will have the same x coordinate and width as the
					 * layout1() bounds argument.  The algorithm is identical to what's
					 * explained above, for the X axis case.
					 */
			else {
				double y = bounds.getY();
				while (splitChildren.hasNext()) {
					final Node splitChild = splitChildren.next();
					final Divider dividerChild = splitChildren.hasNext() ? (Divider) splitChildren.next() : null;

					final double childHeight;
					if (getFloatingDividers()) {
						childHeight = preferredNodeSize(splitChild).getHeight();
					} else {
						if (dividerChild != null) {
							childHeight = dividerChild.getBounds().getY() - y;
						} else {
							childHeight = split.getBounds().getMaxY() - y;
						}
					}
					childBounds = boundsWithYandHeight(bounds, y, childHeight);
					layout1(splitChild, childBounds);

					if (getFloatingDividers() && dividerChild != null) {
						final double dividerY = childBounds.getMaxY();
						final Rectangle dividerBounds = boundsWithYandHeight(bounds, dividerY, dividerSize);
						dividerChild.setBounds(dividerBounds);
					}
					if (dividerChild != null) {
						y = dividerChild.getBounds().getMaxY();
					}
				}
			}
			/* The bounds of the Split node root are set to be just
					 * big enough to contain all of its children, but only
					 * along the axis it's allocating space on.  That's
					 * X for rows, Y for columns.  The second pass of the
					 * layout algorithm - see layoutShrink()/layoutGrow()
					 * allocates extra space.
					 */
			minimizeSplitBounds(split, bounds);
		}
	}


	/**
	 * The specified Node is either the wrong type or was configured
	 * incorrectly.
	 */
	public static class InvalidLayoutException extends RuntimeException {

		private static final long serialVersionUID = 0L;

		private final transient Node node;


		public InvalidLayoutException(final String msg, final Node node) {
			super(msg);
			this.node = node;
		}


		/** @return the invalid Node. */
		public Node getNode() {
			return node;
		}
	}


	private static void throwInvalidLayout(final String msg, final Node node) {
		throw new InvalidLayoutException(msg, node);
	}


	private static void checkLayout(final Node root) {
		if (root instanceof Split) {
			final Split split = (Split) root;
			//if (split.getChildren().size() <= 2) {
				//throwInvalidLayout("Split must have > 2 children", root);
			//}
			final Iterator<Node> splitChildren = split.getChildren().iterator();
			double weight = 0.0;
			while (splitChildren.hasNext()) {
				final Node splitChild = splitChildren.next();
				if (splitChild instanceof Divider) {
					throwInvalidLayout("expected a Split or Leaf Node", splitChild);
				}
				if (splitChildren.hasNext()) {
					final Node dividerChild = splitChildren.next();
					if (!(dividerChild instanceof Divider)) {
						throwInvalidLayout("expected a Divider Node", dividerChild);
					}
				}
				weight += splitChild.getWeight();
				checkLayout(splitChild);
			}
			if (weight > 1.0) {
				throwInvalidLayout("Split children's total weight > 1.0", root);
			}
		}
	}


	/**
	 * Compute the bounds of all of the Split/Divider/Leaf Nodes in
	 * the layout model, and then set the bounds of each child component
	 * with a matching Leaf Node.
	 */
	public void layoutContainer(final Container parent) {
		checkLayout(getModel());
		final Insets insets = parent.getInsets();
		final Dimension size = parent.getSize();
		final int width = size.width - (insets.left + insets.right);
		final int height = size.height - (insets.top + insets.bottom);
		final Rectangle bounds = new Rectangle(insets.left, insets.top, width, height);
		layout1(getModel(), bounds);
		layout2(getModel(), bounds);
	}


	private static Divider dividerAt(final Node root, final int x, final int y) {
		if (root instanceof Divider) {
			final Divider divider = (Divider) root;
			return divider.getBounds().contains(x, y) ? divider : null;
		} else if (root instanceof Split) {
			final Split split = (Split) root;
			for (final Node child : split.getChildren()) {
				if (child.getBounds().contains(x, y)) {
					return dividerAt(child, x, y);
				}
			}
		}
		return null;
	}


	/**
	 * Return the Divider whose bounds contain the specified
	 * point, or null if there isn't one.
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return the Divider at x,y
	 */
	public Divider dividerAt(final int x, final int y) {
		return dividerAt(getModel(), x, y);
	}


	private static boolean nodeOverlapsRectangle(final Node node, final Rectangle r2) {
		final Rectangle r1 = node.getBounds();
		return r1.x <= r2.x + r2.width && r1.x + r1.width >= r2.x && r1.y <= r2.y + r2.height && r1.y + r1.height >= r2.y;
	}


	private static List<Divider> dividersThatOverlap(final Node root, final Rectangle r) {
		if (nodeOverlapsRectangle(root, r) && root instanceof Split) {
			final List<Divider> dividers = new ArrayList<Divider>();
			for (final Node child : ((Split) root).getChildren()) {
				if (child instanceof Divider) {
					if (nodeOverlapsRectangle(child, r)) {
						dividers.add((Divider) child);
					}
				} else if (child instanceof Split) {
					dividers.addAll(dividersThatOverlap(child, r));
				}
			}
			return dividers;
		} else {
			return Collections.emptyList();
		}
	}


	/**
	 * Return the Dividers whose bounds overlap the specified
	 * Rectangle.
	 *
	 * @param r target Rectangle
	 * @return the Dividers that overlap r
	 * @throws IllegalArgumentException if the Rectangle is null
	 */
	public Iterable<Divider> dividersThatOverlap(final Rectangle r) {
		if (r == null) {
			throw new IllegalArgumentException("null Rectangle");
		}
		return dividersThatOverlap(getModel(), r);
	}


	/** Base class for the nodes that model a MultiSplitLayout. */
	public abstract static class Node {

		private Split parent;
		private Rectangle bounds = new Rectangle();
		private double weight;


		/**
		 * Returns the Split parent of this Node, or null.
		 *
		 * @return the value of the parent property.
		 * @see #setParent
		 */
		public Split getParent() {
			return parent;
		}


		/**
		 * Set the value of this Node's parent property.  The default
		 * value of this property is null.
		 *
		 * @param parent a Split or null
		 * @see #getParent
		 */
		public void setParent(final Split parent) {
			this.parent = parent;
		}


		/**
		 * Returns the bounding Rectangle for this Node.
		 *
		 * @return the value of the bounds property.
		 * @see #setBounds
		 */
		public Rectangle getBounds() {
			return new Rectangle(bounds);
		}


		/**
		 * Set the bounding Rectangle for this node.  The value of
		 * bounds may not be null.  The default value of bounds
		 * is equal to <code>new Rectangle(0,0,0,0)</code>.
		 *
		 * @param bounds the new value of the bounds property
		 * @throws IllegalArgumentException if bounds is null
		 * @see #getBounds
		 */
		public void setBounds(final Rectangle bounds) {
			if (bounds == null) {
				throw new IllegalArgumentException("null bounds");
			}
			this.bounds = new Rectangle(bounds);
		}


		/**
		 * Value between 0.0 and 1.0 used to compute how much space
		 * to add to this sibling when the layout grows or how
		 * much to reduce when the layout shrinks.
		 *
		 * @return the value of the weight property
		 * @see #setWeight
		 */
		public double getWeight() {
			return weight;
		}


		/**
		 * The weight property is a between 0.0 and 1.0 used to
		 * compute how much space to add to this sibling when the
		 * layout grows or how much to reduce when the layout shrinks.
		 * If rowLayout is true then this node's width grows
		 * or shrinks by (extraSpace * weight).  If rowLayout is false,
		 * then the node's height is changed.  The default value
		 * of weight is 0.0.
		 *
		 * @param weight a double between 0.0 and 1.0
		 * @throws IllegalArgumentException if weight is not between 0.0 and 1.0
		 * @see #getWeight
		 * @see MultiSplitLayout#layoutContainer
		 */
		public void setWeight(final double weight) {
			if (weight < 0.0 || weight > 1.0) {
				throw new IllegalArgumentException("invalid weight");
			}
			this.weight = weight;
		}


		private Node siblingAtOffset(final int offset) {
			final Split parent = getParent();
			if (parent == null) {
				return null;
			}
			final List<Node> siblings = parent.getChildren();
			int index = siblings.indexOf(this);
			if (index == -1) {
				return null;
			}
			index += offset;
			return index > -1 && index < siblings.size() ? siblings.get(index) : null;
		}


		/**
		 * Return the Node that comes after this one in the parent's
		 * list of children, or null.  If this node's parent is null,
		 * or if it's the last child, then return null.
		 *
		 * @return the Node that comes after this one in the parent's list of children.
		 * @see #previousSibling
		 * @see #getParent
		 */
		public Node nextSibling() {
			return siblingAtOffset(+1);
		}


		/**
		 * Return the Node that comes before this one in the parent's
		 * list of children, or null.  If this node's parent is null,
		 * or if it's the last child, then return null.
		 *
		 * @return the Node that comes before this one in the parent's list of children.
		 * @see #nextSibling
		 * @see #getParent
		 */
		public Node previousSibling() {
			return siblingAtOffset(-1);
		}
	}

	/**
	 * Defines a vertical or horizontal subdivision into two or more
	 * tiles.
	 */
	public static class Split extends Node {

		private List<Node> children = Collections.emptyList();
		private boolean rowLayout = true;


		/**
		 * Returns true if the this Split's children are to be
		 * laid out in a row: all the same height, left edge
		 * equal to the previous Node's right edge.  If false,
		 * children are laid on in a column.
		 *
		 * @return the value of the rowLayout property.
		 * @see #setRowLayout
		 */
		public boolean isRowLayout() {
			return rowLayout;
		}


		/**
		 * Set the rowLayout property.  If true, all of this Split's
		 * children are to be laid out in a row: all the same height,
		 * each node's left edge equal to the previous Node's right
		 * edge.  If false, children are laid on in a column.  Default
		 * value is true.
		 *
		 * @param rowLayout true for horizontal row layout, false for column
		 * @see #isRowLayout
		 */
		public void setRowLayout(final boolean rowLayout) {
			this.rowLayout = rowLayout;
		}


		/**
		 * Returns this Split node's children.  The returned value
		 * is not a reference to the Split's internal list of children
		 *
		 * @return the value of the children property.
		 * @see #setChildren
		 */
		public List<Node> getChildren() {
			return new ArrayList<Node>(children);
		}


		/**
		 * Set's the children property of this Split node.  The parent
		 * of each new child is set to this Split node, and the parent
		 * of each old child (if any) is set to null.  This method
		 * defensively copies the incoming List.  Default value is
		 * an empty List.
		 *
		 * @param children List of children
		 * @throws IllegalArgumentException if children is null
		 * @see #getChildren
		 */
		public void setChildren(final List<Node> children) {
			if (children == null) {
				throw new IllegalArgumentException("children must be a non-null List");
			}
			for (final Node child : this.children) {
				child.setParent(null);
			}
			this.children = new ArrayList<Node>(children);
			for (final Node child : this.children) {
				child.setParent(this);
			}
		}


		/**
		 * Convenience method that returns the last child whose weight
		 * is > 0.0.
		 *
		 * @return the last child whose weight is > 0.0.
		 * @see #getChildren
		 * @see Node#getWeight
		 */
		public final Node lastWeightedChild() {
			final List<Node> children = getChildren();
			Node weightedChild = null;
			for (final Node child : children) {
				if (child.getWeight() > 0.0) {
					weightedChild = child;
				}
			}
			return weightedChild;
		}


		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("Split");
			sb.append("{children=").append(children);
			sb.append(", rowLayout=").append(rowLayout);
			sb.append('}');
			return sb.toString();
		}
	}


	/** Models a java.awt Component child. */
	public static class Leaf extends Node {

		private String name = "";


		/** Create a Leaf node.  The default value of name is "". */
		public Leaf() {
		}


		/**
		 * Create a Leaf node with the specified name.  Name can not
		 * be null.
		 *
		 * @param name value of the Leaf's name property
		 * @throws IllegalArgumentException if name is null
		 */
		public Leaf(final String name) {
			if (name == null) {
				throw new IllegalArgumentException("name is null");
			}
			this.name = name;
		}


		/**
		 * Return the Leaf's name.
		 *
		 * @return the value of the name property.
		 * @see #setName
		 */
		public String getName() {
			return name;
		}


		/**
		 * Set the value of the name property.  Name may not be null.
		 *
		 * @param name value of the name property
		 * @throws IllegalArgumentException if name is null
		 */
		public void setName(final String name) {
			if (name == null) {
				throw new IllegalArgumentException("name is null");
			}
			this.name = name;
		}


		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("Leaf");
			sb.append("{name='").append(name).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}


	/** Models a single vertical/horiztonal divider. */
	public static class Divider extends Node {

		/**
		 * Convenience method, returns true if the Divider's parent
		 * is a Split row (a Split with isRowLayout() true), false
		 * otherwise. In other words if this Divider's major axis
		 * is vertical, return true.
		 *
		 * @return true if this Divider is part of a Split row.
		 */
		public final boolean isVertical() {
			final Split parent = getParent();
			return parent != null && parent.isRowLayout();
		}


		/**
		 * Dividers can't have a weight, they don't grow or shrink.
		 *
		 * @throws UnsupportedOperationException
		 */
		@Override
		public void setWeight(final double weight) {
			throw new UnsupportedOperationException();
		}


		@Override
		public String toString() {
			return "MultiSplitLayout.Divider " + getBounds().toString();
		}
	}


	private static void throwParseException(final StreamTokenizer st, final String msg) throws Exception {
		throw new Exception("MultiSplitLayout.parseModel Error: " + msg);
	}


	private static void parseAttribute(final String name, final StreamTokenizer st, final Node node) throws Exception {
		if (st.nextToken() != '=') {
			throwParseException(st, "expected '=' after " + name);
		}
		if ("WEIGHT".equalsIgnoreCase(name)) {
			if (st.nextToken() == StreamTokenizer.TT_NUMBER) {
				node.setWeight(st.nval);
			} else {
				throwParseException(st, "invalid weight");
			}
		} else if ("NAME".equalsIgnoreCase(name)) {
			if (st.nextToken() == StreamTokenizer.TT_WORD) {
				if (node instanceof Leaf) {
					((Leaf) node).setName(st.sval);
				} else {
					throwParseException(st, "can't specify name for " + node);
				}
			} else {
				throwParseException(st, "invalid name");
			}
		} else {
			throwParseException(st, "unrecognized attribute \"" + name + '"');
		}
	}


	private static void addSplitChild(final Split parent, final Node child) {
		final List<Node> children = new ArrayList<Node>(parent.getChildren());
		if (children.isEmpty()) {
			children.add(child);
		} else {
			children.add(new Divider());
			children.add(child);
		}
		parent.setChildren(children);
	}


	private static void parseLeaf(final StreamTokenizer st, final Split parent) throws Exception {
		final Leaf leaf = new Leaf();
		int token;
		while ((token = st.nextToken()) != StreamTokenizer.TT_EOF) {
			if (token == ')') {
				break;
			}
			if (token == StreamTokenizer.TT_WORD) {
				parseAttribute(st.sval, st, leaf);
			} else {
				throwParseException(st, "Bad Leaf: " + leaf);
			}
		}
		addSplitChild(parent, leaf);
	}


	private static void parseSplit(final StreamTokenizer st, final Split parent) throws Exception {
		int token;
		while ((token = st.nextToken()) != StreamTokenizer.TT_EOF) {
			if (token == ')') {
				break;
			} else if (token == StreamTokenizer.TT_WORD) {
				if ("WEIGHT".equalsIgnoreCase(st.sval)) {
					parseAttribute(st.sval, st, parent);
				} else {
					addSplitChild(parent, new Leaf(st.sval));
				}
			} else if (token == '(') {
				if (st.nextToken() != StreamTokenizer.TT_WORD) {
					throwParseException(st, "invalid node type");
				}
				final String nodeType = st.sval.toUpperCase(Locale.ENGLISH);
				if ("LEAF".equals(nodeType)) {
					parseLeaf(st, parent);
				} else if ("ROW".equals(nodeType) || "COLUMN".equals(nodeType)) {
					final Split split = new Split();
					split.setRowLayout("ROW".equals(nodeType));
					addSplitChild(parent, split);
					parseSplit(st, split);
				} else {
					throwParseException(st, "unrecognized node type '" + nodeType + '\'');
				}
			}
		}
	}


	private static Node parseModel(final Reader r) {
		final StreamTokenizer st = new StreamTokenizer(r);
		try {
			final Split root = new Split();
			parseSplit(st, root);
			return root.getChildren().get(0);
		} catch (final Exception e) {
			//noinspection UseOfSystemOutOrSystemErr
			System.err.println(e);
		} finally {
			try {
				r.close();
			} catch (final IOException ignore) {
			}
		}
		return null;
	}


	/**
	 * A convenience method that converts a string to a
	 * MultiSplitLayout model (a tree of Nodes) using a
	 * a simple syntax.  Nodes are represented by
	 * parenthetical expressions whose first token
	 * is one of ROW/COLUMN/LEAF.  ROW and COLUMN specify
	 * horizontal and vertical Split nodes respectively,
	 * LEAF specifies a Leaf node.  A Leaf's name and
	 * weight can be specified with attributes,
	 * name=<i>myLeafName</i> weight=<i>myLeafWeight</i>.
	 * Similarly, a Split's weight can be specified with
	 * weight=<i>mySplitWeight</i>.
	 * <p/>
	 * <p> For example, the following expression generates
	 * a horizontal Split node with three children:
	 * the Leafs named left and right, and a Divider in
	 * between:
	 * <pre>
	 * (ROW (LEAF name=left) (LEAF name=right weight=1.0))
	 * </pre>
	 * <p/>
	 * <p> Dividers should not be included in the string,
	 * they're added automatcially as needed.  Because
	 * Leaf nodes often only need to specify a name, one
	 * can specify a Leaf by just providing the name.
	 * The previous example can be written like this:
	 * <pre>
	 * (ROW left (LEAF name=right weight=1.0))
	 * </pre>
	 * <p/>
	 * <p>Here's a more complex example.  One row with
	 * three elements, the first and last of which are columns
	 * with two leaves each:
	 * <pre>
	 * (ROW (COLUMN weight=0.5 left.top left.bottom)
	 *      (LEAF name=middle)
	 *      (COLUMN weight=0.5 right.top right.bottom))
	 * </pre>
	 * <p/>
	 * <p/>
	 * <p> This syntax is not intended for archiving or
	 * configuration files .  It's just a convenience for
	 * examples and tests.
	 *
	 * @param s
	 * @return the Node root of a tree based on s.
	 */
	public static Node parseModel(final String s) {
		return parseModel(new StringReader(s));
	}


	private static void printModel(final String indent, final Node root) {
		if (root instanceof Split) {
			final Split split = (Split) root;
			//noinspection UseOfSystemOutOrSystemErr
			System.out.println(indent + split);
			for (final Node child : split.getChildren()) {
				printModel(indent + "  ", child);
			}
		} else {
			//noinspection UseOfSystemOutOrSystemErr
			System.out.println(indent + root);
		}
	}


	/** Print the tree with enough detail for simple debugging.
	 * @param root*/
	public static void printModel(final Node root) {
		printModel("", root);
	}
}
