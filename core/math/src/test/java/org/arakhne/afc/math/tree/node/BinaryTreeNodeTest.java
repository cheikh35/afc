/* 
 * $Id$
 * 
 * Copyright (c) 2005-10, Multiagent Team,
 * Laboratoire Systemes et Transports,
 * Universite de Technologie de Belfort-Montbeliard.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of the Laboratoire Systemes et Transports
 * of the Universite de Technologie de Belfort-Montbeliard ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with the SeT.
 *
 * http://www.multiagent.fr/
 */
package org.arakhne.afc.math.tree.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.tree.node.BinaryTreeNode.BinaryTreeZone;
import org.arakhne.afc.math.tree.node.BinaryTreeNode.DefaultBinaryTreeNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class BinaryTreeNodeTest {

	private final TreeNodeListenerStub<DefaultBinaryTreeNode<Object>> listener =
		new TreeNodeListenerStub<>();
	
	private DefaultBinaryTreeNode<Object> root;
	private DefaultBinaryTreeNode<Object> child1;
	private DefaultBinaryTreeNode<Object> child2;
	private DefaultBinaryTreeNode<Object> node;
	private DefaultBinaryTreeNode<Object> newNode;
	
	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.root = new NodeStub("root"); //$NON-NLS-1$
		this.child1 = new NodeStub("child1"); //$NON-NLS-1$
		this.child2 = new NodeStub("child2"); //$NON-NLS-1$
		this.node = new NodeStub("node"); //$NON-NLS-1$
		this.newNode = new NodeStub("newNode"); //$NON-NLS-1$
		
		this.root.setChildAt(BinaryTreeZone.LEFT, this.child1);
		this.root.setChildAt(BinaryTreeZone.RIGHT, this.child2);
		this.child1.setChildAt(BinaryTreeZone.LEFT, this.node);
	
		this.listener.reset();
		this.root.addTreeNodeListener(this.listener);
	}
	
	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.root.removeTreeNodeListener(this.listener);
		this.root.clear();
		this.root = this.child1 = this.child2 = this.node = this.newNode = null;
		this.listener.reset();
	}

	/**
	 */
	@Test
	public void getChildCount() {
		assertEquals(2, this.root.getChildCount());
		assertEquals(2, this.child1.getChildCount());
		assertEquals(2, this.child2.getChildCount());
		assertEquals(2, this.node.getChildCount());
	}

	/**
	 */
	@Test
	public void getNotNullChildCount() {
		assertEquals(2, this.root.getNotNullChildCount());
		assertEquals(1, this.child1.getNotNullChildCount());
		assertEquals(0, this.child2.getNotNullChildCount());
		assertEquals(0, this.node.getNotNullChildCount());
	}
	
	/**
	 */
	@Test
	public void setLeftChild_newNode() {
		this.node.setLeftChild(this.newNode);
		
		assertSame(this.newNode, this.node.getLeftChild());
		assertSame(this.node, this.newNode.getParentNode());
		
		assertEquals(1, this.listener.additionEvent.size());
		assertEquals(0, this.listener.removalEvent.size());
		assertEquals(0, this.listener.dataEvent.size());
		assertEquals(1, this.listener.parentEvent.size());
		
		assertSame(this.node, this.listener.additionEvent.get(0).getSource());
		assertSame(this.newNode, this.listener.additionEvent.get(0).getChild());
		assertSame(this.node, this.listener.additionEvent.get(0).getParentNode());
		assertSame(0, this.listener.additionEvent.get(0).getChildIndex());

		assertSame(this.newNode, this.listener.parentEvent.get(0).getSource());
		assertSame(this.newNode, this.listener.parentEvent.get(0).getChildNode());
		assertNull(this.listener.parentEvent.get(0).getOldParent());
		assertSame(this.node, this.listener.parentEvent.get(0).getNewParent());
	}
	
	/**
	 */
	@Test
	public void setLeftChild_moveNode() {
		this.node.setLeftChild(this.child2);
		
		assertSame(this.child2, this.node.getLeftChild());
		assertSame(this.node, this.child2.getParentNode());
		
		assertEquals(1, this.listener.additionEvent.size());
		assertEquals(1, this.listener.removalEvent.size());
		assertEquals(0, this.listener.dataEvent.size());
		assertEquals(2, this.listener.parentEvent.size());
		
		assertSame(this.node, this.listener.additionEvent.get(0).getSource());
		assertSame(this.child2, this.listener.additionEvent.get(0).getChild());
		assertSame(this.node, this.listener.additionEvent.get(0).getParentNode());
		assertSame(0, this.listener.additionEvent.get(0).getChildIndex());

		assertSame(this.child2, this.listener.parentEvent.get(0).getSource());
		assertSame(this.child2, this.listener.parentEvent.get(0).getChildNode());
		assertSame(this.root, this.listener.parentEvent.get(0).getOldParent());
		assertNull(this.listener.parentEvent.get(0).getNewParent());

		assertSame(this.child2, this.listener.parentEvent.get(1).getSource());
		assertSame(this.child2, this.listener.parentEvent.get(1).getChildNode());
		assertNull(this.listener.parentEvent.get(1).getOldParent());
		assertSame(this.node, this.listener.parentEvent.get(1).getNewParent());

		assertSame(this.root, this.listener.removalEvent.get(0).getSource());
		assertSame(this.child2, this.listener.removalEvent.get(0).getChild());
		assertSame(this.root, this.listener.removalEvent.get(0).getParentNode());
		assertSame(1, this.listener.removalEvent.get(0).getChildIndex());
	}

	/**
	 */
	@Test
	public void setRightChild_newNode() {
		this.node.setRightChild(this.newNode);
		
		assertSame(this.newNode, this.node.getRightChild());
		assertSame(this.node, this.newNode.getParentNode());
		
		assertEquals(1, this.listener.additionEvent.size());
		assertEquals(0, this.listener.removalEvent.size());
		assertEquals(0, this.listener.dataEvent.size());
		assertEquals(1, this.listener.parentEvent.size());
		
		assertSame(this.node, this.listener.additionEvent.get(0).getSource());
		assertSame(this.newNode, this.listener.additionEvent.get(0).getChild());
		assertSame(this.node, this.listener.additionEvent.get(0).getParentNode());
		assertSame(1, this.listener.additionEvent.get(0).getChildIndex());

		assertSame(this.newNode, this.listener.parentEvent.get(0).getSource());
		assertSame(this.newNode, this.listener.parentEvent.get(0).getChildNode());
		assertNull(this.listener.parentEvent.get(0).getOldParent());
		assertSame(this.node, this.listener.parentEvent.get(0).getNewParent());
	}
	
	/**
	 */
	@Test
	public void setRightChild_moveNode() {
		this.node.setRightChild(this.child2);
		
		assertSame(this.child2, this.node.getRightChild());
		assertSame(this.node, this.child2.getParentNode());
		
		assertEquals(1, this.listener.additionEvent.size());
		assertEquals(1, this.listener.removalEvent.size());
		assertEquals(0, this.listener.dataEvent.size());
		assertEquals(2, this.listener.parentEvent.size());
		
		assertSame(this.node, this.listener.additionEvent.get(0).getSource());
		assertSame(this.child2, this.listener.additionEvent.get(0).getChild());
		assertSame(this.node, this.listener.additionEvent.get(0).getParentNode());
		assertSame(1, this.listener.additionEvent.get(0).getChildIndex());

		assertSame(this.child2, this.listener.parentEvent.get(0).getSource());
		assertSame(this.child2, this.listener.parentEvent.get(0).getChildNode());
		assertSame(this.root, this.listener.parentEvent.get(0).getOldParent());
		assertNull(this.listener.parentEvent.get(0).getNewParent());

		assertSame(this.child2, this.listener.parentEvent.get(1).getSource());
		assertSame(this.child2, this.listener.parentEvent.get(1).getChildNode());
		assertNull(this.listener.parentEvent.get(1).getOldParent());
		assertSame(this.node, this.listener.parentEvent.get(1).getNewParent());

		assertSame(this.root, this.listener.removalEvent.get(0).getSource());
		assertSame(this.child2, this.listener.removalEvent.get(0).getChild());
		assertSame(this.root, this.listener.removalEvent.get(0).getParentNode());
		assertSame(1, this.listener.removalEvent.get(0).getChildIndex());
	}

	/**
	 */
	@Test
	public void moveToNodeInt() {
		assertTrue(this.child2.moveTo(this.node, 0));
		
		assertSame(this.child2, this.node.getLeftChild());
		assertSame(this.node, this.child2.getParentNode());
		
		assertEquals(1, this.listener.additionEvent.size());
		assertEquals(1, this.listener.removalEvent.size());
		assertEquals(0, this.listener.dataEvent.size());
		assertEquals(1, this.listener.parentEvent.size());
		
		assertSame(this.node, this.listener.additionEvent.get(0).getSource());
		assertSame(this.child2, this.listener.additionEvent.get(0).getChild());
		assertSame(this.node, this.listener.additionEvent.get(0).getParentNode());
		assertSame(0, this.listener.additionEvent.get(0).getChildIndex());

		assertSame(this.child2, this.listener.parentEvent.get(0).getSource());
		assertSame(this.child2, this.listener.parentEvent.get(0).getChildNode());
		assertSame(this.root, this.listener.parentEvent.get(0).getOldParent());
		assertSame(this.node, this.listener.parentEvent.get(0).getNewParent());

		assertSame(this.root, this.listener.removalEvent.get(0).getSource());
		assertSame(this.child2, this.listener.removalEvent.get(0).getChild());
		assertSame(this.root, this.listener.removalEvent.get(0).getParentNode());
		assertSame(1, this.listener.removalEvent.get(0).getChildIndex());
	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class NodeStub extends DefaultBinaryTreeNode<Object> {

		private static final long serialVersionUID = -1123134017423112775L;
		
		private final String name;
		
		/**
		 * @param name
		 */
		public NodeStub(String name) {
			this.name = name;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return this.name;
		}
		
	}
	
}