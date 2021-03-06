/*
 * Copyright (c) 2010, Frederik Vanhoutte This library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * http://creativecommons.org/licenses/LGPL/2.1/ This library is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU Lesser General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */
package wblut.hemesh.modifiers;

import java.util.Iterator;

import wblut.geom.WB_Normal;
import wblut.hemesh.core.HE_Mesh;
import wblut.hemesh.core.HE_Selection;
import wblut.hemesh.core.HE_Vertex;
import wblut.random.WB_RandomSphere;

// TODO: Auto-generated Javadoc
/**
 * Expands or contracts all vertices along the vertex normals.
 * 
 * @author Frederik Vanhoutte (W:Blut)
 * 
 */

public class HEM_Noise extends HEM_Modifier {

	/** Expansion distance. */
	private double	d;

	/**
	 * 
	 */
	public HEM_Noise() {

		super();
	}

	/**
	 * Set distance to move vertices.
	 *
	 * @param d distance
	 * @return this
	 */
	public HEM_Noise setDistance(final double d) {
		this.d = d;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see wblut.hemesh.HE_Modifier#apply(wblut.hemesh.HE_Mesh)
	 */
	@Override
	public HE_Mesh apply(final HE_Mesh mesh) {
		if (d == 0) {
			return mesh;
		}
		HE_Vertex v;
		final Iterator<HE_Vertex> vItr = mesh.vItr();

		final WB_RandomSphere rs = new WB_RandomSphere();
		WB_Normal n;
		while (vItr.hasNext()) {
			v = vItr.next();
			n = rs.nextNormal();
			v.add(n.mult(d));
		}
		return mesh;
	}

	/*
	 * (non-Javadoc)
	 * @see wblut.hemesh.HE_Modifier#apply(wblut.hemesh.HE_Mesh)
	 */
	@Override
	public HE_Mesh apply(final HE_Selection selection) {

		if (d == 0) {
			return selection.parent;
		}
		selection.collectVertices();
		final Iterator<HE_Vertex> vItr = selection.vItr();

		HE_Vertex v;

		final WB_RandomSphere rs = new WB_RandomSphere();
		WB_Normal n;
		while (vItr.hasNext()) {
			v = vItr.next();
			n = rs.nextNormal();
			v.add(n.mult(d));
		}

		return selection.parent;
	}
}
