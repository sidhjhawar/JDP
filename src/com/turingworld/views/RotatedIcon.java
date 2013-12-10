package com.turingworld.views;

/**
 * @author bbachuna, chauhanp, erajan, haashraf, sjhawar, vrajasek.
 */
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.Icon;
import com.turingworld.views.DFABuilderView.Rotate;

public class RotatedIcon implements Icon {

	private Icon icon;
	private Rotate rotate;
	private double angle;
	private double radians;
	private double sin;
	private double cos;
	private int width;
	private int height;

	public RotatedIcon(Icon icon) {
		this(icon, Rotate.UP);
	}

	public RotatedIcon(Icon icon, Rotate rotate) {
		this.icon = icon;
		this.rotate = rotate;
	}

	public RotatedIcon(Icon icon, double angle) {
		this(icon, Rotate.ABOUT_CENTER);
		this.angle = angle;
	}

	public Icon getIcon() {
		return icon;
	}

	public Rotate getRotate() {
		return rotate;
	}

	public double getAngle() {
		return angle;
	}

	public int getIconWidth() {
		if (rotate == Rotate.ABOUT_CENTER) {
			radians = Math.toRadians(angle);
			sin = Math.abs(Math.sin(radians));
			cos = Math.abs(Math.cos(radians));
			width = (int) Math.floor(icon.getIconWidth() * cos + icon.getIconHeight() * sin);
			return width;
		} else if (rotate == Rotate.UPSIDE_DOWN)
			return icon.getIconWidth();
		else
			return icon.getIconHeight();
	}

	@Override
	public int getIconHeight() {
		if (rotate == Rotate.ABOUT_CENTER) {
			radians = Math.toRadians(angle);
			sin = Math.abs(Math.sin(radians));
			cos = Math.abs(Math.cos(radians));
			height = (int) Math.floor(icon.getIconHeight() * cos + icon.getIconWidth() * sin);
			return height;
		} else if (rotate == Rotate.UPSIDE_DOWN)
			return icon.getIconHeight();
		else
			return icon.getIconWidth();
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();

		int cWidth = icon.getIconWidth() / 2;
		int cHeight = icon.getIconHeight() / 2;
		int xAdjustment = (icon.getIconWidth() % 2) == 0 ? 0 : -1;
		int yAdjustment = (icon.getIconHeight() % 2) == 0 ? 0 : -1;

		if (rotate == Rotate.DOWN) {
			g2.translate(x + cHeight, y + cWidth);
			g2.rotate(Math.toRadians(90));
			icon.paintIcon(c, g2, -cWidth, yAdjustment - cHeight);
		} else if (rotate == Rotate.UP) {
			g2.translate(x + cHeight, y + cWidth);
			g2.rotate(Math.toRadians(-90));
			icon.paintIcon(c, g2, xAdjustment - cWidth, -cHeight);
		} else if (rotate == Rotate.UPSIDE_DOWN) {
			g2.translate(x + cWidth, y + cHeight);
			g2.rotate(Math.toRadians(180));
			icon.paintIcon(c, g2, xAdjustment - cWidth, yAdjustment - cHeight);
		} else if (rotate == Rotate.ABOUT_CENTER) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			AffineTransform original = g2.getTransform();
			AffineTransform at = new AffineTransform();
			at.concatenate(original);
			at.translate((getIconWidth() - icon.getIconWidth()) / 2, (getIconHeight() - icon.getIconHeight()) / 2);
			at.rotate(Math.toRadians(angle), x + cWidth, y + cHeight);
			g2.setTransform(at);
			icon.paintIcon(c, g2, x, y);
			g2.setTransform(original);
		}
	}
}