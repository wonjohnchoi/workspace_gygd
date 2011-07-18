
/* MissileCommando.java - based on the arcade game Missile Command. */

/* 

 * Copyright (C) 1995 Mark Boyns <boyns@sdsu.edu>

 *

 * MissileCommando

 * <URL:http://www.sdsu.edu/~boyns/java/mc/>

 *

 * This program is free software; you can redistribute it and/or modify

 * it under the terms of the GNU General Public License as published by

 * the Free Software Foundation; either version 2 of the License, or

 * (at your option) any later version.

 *

 * This program is distributed in the hope that it will be useful,

 * but WITHOUT ANY WARRANTY; without even the implied warranty of

 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the

 * GNU General Public License for more details.

 *

 * You should have received a copy of the GNU General Public License

 * along with this program; if not, write to the Free Software

 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.

 */

import java.applet.*;

import java.awt.*;

import java.util.Enumeration;

import java.util.Vector;

/* Abstract class used for moving objects, etc. */

abstract class Thing extends Thread

{

	MissileCommando parent;

	public int X;

	public int Y;

	Color color;

	abstract void paint(Graphics g); /* paint the object */

	abstract void erase(Graphics g); /* erase the object */

	abstract boolean hit(Missile m); /* did this missile hit the object? */

	abstract void explode(); /* explode the object */

}

/*
 * A simple rectangular base. This object just sits around waiting to
 * 
 * explode.
 */

class Base extends Thing

{

	private int W = 0;

	private int H = 0;

	Base(MissileCommando parent, Color color, int x, int y, int w, int h)

	{

		this.parent = parent;

		X = x;

		Y = y;

		this.color = color;

		W = w;

		H = h;

		start();

	}

	public void run()

	{

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		waitToDie();

	}

	public synchronized void waitToDie()

	{

		try

		{

			wait();

		}

		catch (InterruptedException e)

		{

		}

	}

	public synchronized void timeToDie()

	{

		notify();

	}

	public boolean hit(Missile m)

	{

		return (m.X >= X && m.X <= (X + W)

		&& m.Y >= Y && m.Y <= (Y + H));

	}

	public void explode()

	{

		timeToDie();

		stop();

	}

	public void paint(Graphics g)

	{

		g.setColor(color);

		g.fillRect(X, Y, W, H);

	}

	public void erase(Graphics g)

	{

		g.setColor(Color.lightGray);

		g.fillRect(X, Y, W, H);

	}

}

/*
 * A missile which moves from X1,Y1 to X2,Y2 with velocity V. A
 * 
 * missile can randomly replicate itself into 3 missiles.
 */

class Missile extends Thing

{

	private boolean paintme = false;

	private int X1 = 0;

	private int Y1 = 0;

	private int X2 = 0;

	private int Y2 = 0;

	private int V = 0;

	private float m = 0;

	private float b = 0;

	private float x = 0;

	private float y = 0;

	private boolean replicate = false;

	Missile(MissileCommando parent, Color color, int x1, int y1, int x2,
			int y2, int v)

	{

		this.parent = parent;

		X = X1;

		Y = Y1;

		this.color = color;

		X1 = x1;

		Y1 = y1;

		X2 = x2;

		Y2 = y2;

		V = v;

		start();

	}

	public void run()

	{

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		m = (float) (Y2 - Y1) / (X2 - X1);

		b = Y1 - (m * X1);

		x = X1;

		y = Y1;

		/* 10% chance this missile will replicate. */

		replicate = Math.random() > 0.90;

		if (replicate)

		{

			color = Color.magenta;

		}

		while (y <= Y2)

		{

			/* 25% chance this missile will replicate now. */

			if (replicate && y > Y1 + 5 * V && Math.random() > 0.75)

			{

				for (int i = 0; i < 3; i++)

				{

					parent.createMissile(X, Y, V);

				}

				/* stop the current missile */

				break;

			}

			paintme = true;

			parent.repaint();

			try

			{

				Thread.sleep(100);

			}

			catch (InterruptedException e)

			{

			}

		}

		paintme = true;

		parent.repaint();

	}

	public boolean hit(Missile m)

	{

		return m.X == X && m.Y == Y;

	}

	public void explode()

	{

		stop();

	}

	public void paint(Graphics g)

	{

		if (paintme)

		{

			g.setColor(Color.lightGray);

			g.drawLine(X1, Y1, X, Y);

			y += V;

			x = (y - b) / m;

			X = (int) x;

			Y = (int) y;

			g.setColor(color);

			g.drawLine(X1, Y1, X, Y);

			paintme = false;

		}

	}

	public void erase(Graphics g)

	{

		g.setColor(Color.lightGray);

		g.drawLine(X1, Y1, X, Y);

	}

}

/*
 * Generic explosion which draws a circle that grows and shrinks. The
 * 
 * explosion is draw at X,Y and has a maximum size of S.
 */

class Explosion extends Thing

{

	private boolean paintme = false;

	private int S = 0;

	public int size = 0;

	private int scale = 0;

	Explosion(MissileCommando parent, Color color, int x, int y, int s)

	{

		this.parent = parent;

		X = x;

		Y = y;

		this.color = color;

		S = s;

		start();

	}

	public void run()

	{

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		/* grow */

		scale = 10;

		size = 1;

		do

		{

			paintme = true;

			parent.repaint();

			try

			{

				Thread.sleep(50);

			}

			catch (InterruptedException e)

			{

			}

		}

		while (size <= S);

		/* shrink */

		scale = -10;

		do

		{

			paintme = true;

			parent.repaint();

			try

			{

				Thread.sleep(100);

			}

			catch (InterruptedException e)

			{

			}

		}

		while (size >= 0);

		paintme = true;

		parent.repaint();

	}

	public boolean hit(Missile m)

	{

		return (m.X >= (X - size / 2) && m.X <= (X + size / 2)

		&& m.Y >= (Y - size / 2) && m.Y <= (Y + size / 2));

	}

	public void explode()

	{

	}

	public void paint(Graphics g)

	{

		if (paintme)

		{

			if (scale < 0)

			{

				g.setColor(Color.lightGray);

				g.fillOval(X - size / 2, Y - size / 2, size, size);

			}

			size += scale;

			g.setColor(color);

			g.fillOval(X - size / 2, Y - size / 2, size, size);

			paintme = false;

		}

	}

	public void erase(Graphics g)

	{

	}

}

/* A shot explosion base on Explosion. */

class ShotExplosion extends Explosion

{

	ShotExplosion(MissileCommando parent, int x, int y)

	{

		super(parent, Color.black, x, y, 60);

	}

}

/* A base explosion base on Explosion. */

class BaseExplosion extends Explosion

{

	BaseExplosion(MissileCommando parent, int x, int y)

	{

		super(parent, Color.red, x, y, 100);

	}

}

/* Draw a blinking message. */

class Message extends Thing

{

	private String message;

	private int blinks;

	private int delay;

	private int blinkCount;

	Message(MissileCommando parent, String message, int x, int y, int blinks,
			int delay)

	{

		this.parent = parent;

		this.message = message;

		X = x;

		Y = y;

		this.blinks = blinks;

		this.delay = delay;

		color = Color.black;

		start();

	}

	Message(MissileCommando parent, String message, int x, int y, int blinks)

	{

		this(parent, message, x, y, blinks, 500);

	}

	Message(MissileCommando parent, String message, int x, int y)

	{

		this(parent, message, x, y, 3, 500);

	}

	public void run()

	{

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		for (blinkCount = 0; blinkCount < 2 * blinks; blinkCount++)

		{

			parent.repaint();

			try

			{

				Thread.sleep(delay);

			}

			catch (InterruptedException e)

			{

			}

		}

		parent.repaint();

	}

	public void paint(Graphics g)

	{

		if ((blinkCount % 2) == 0)

		{

			g.setColor(color);

		}

		else

		{

			g.setColor(Color.lightGray);

		}

		g.drawString(message, X, Y);

	}

	public void erase(Graphics g)

	{

		g.setColor(Color.lightGray);

		g.drawString(message, X, Y);

	}

	public boolean hit(Missile m)

	{

		return false;

	}

	public void explode()

	{

	}

}

/* Semaphore class used to synchronize threads. */

class Semaphore

{

	private boolean taken;

	Semaphore()

	{

		taken = false;

	}

	Semaphore(boolean taken)

	{

		this.taken = taken;

	}

	public synchronized void take()

	{

		while (taken)

		{

			try

			{

				wait();

			}

			catch (Exception e)

			{

			}

		}

		taken = true;

	}

	public synchronized boolean peek()

	{

		return taken;

	}

	public synchronized void give()

	{

		taken = false;

		notify();

	}

}

/* The main applet. */

public class MissileCommando extends java.applet.Applet implements Runnable

{

	/* Screen sizes. */

	private final int SCORE_WIDTH = 70;

	private final int WORLD_WIDTH = 430;

	private final int WORLD_HEIGHT = 300;

	/* Base parameters. */

	private final int BASES = 5;

	private final int BASE_SPACING = 30;

	private final int BASE_WIDTH = 50;

	private final int BASE_HEIGHT = 40;

	/* Points */

	private final int POINTS_MISSILE = 100;

	private final int POINTS_EXTRA_SHOTS = 50;

	private final int POINTS_BASE = 100;

	private final int POINTS_NEW_BASE = 5000;

	/* Limit the number of concurrent shots. */

	private final int MAX_SHOTS = 10;

	private int shots = 0;

	/* The sounds. */

	private AudioClip startSound = null;

	private AudioClip applauseSound = null;

	private AudioClip missileSound = null;

	private AudioClip shotExplosionSound = null;

	private AudioClip missileExplosionSound = null;

	private AudioClip baseExplosionSound = null;

	private AudioClip music = null;

	/* State variables. */

	private boolean playing = false;

	private boolean clearScreen = false;

	private boolean loadingSounds = false;

	/* Current game parameters. */

	private int score = 0;

	private int level = 0;

	private int speed = 0;

	private int delay = 0;

	private int missileCount = 0;

	private int shotCount = 0;

	/* Synchronization semaphores. */

	private Semaphore missileSemaphore;

	private Semaphore messageSemaphore;

	/* Fonts */

	private Font font;

	private FontMetrics fontMetrics;

	/* Strings */

	private String scoreString = "Score";

	private String levelString = "Level";

	private String shotString = "Shots";

	private String welcomeString = "Click to start";

	private String loadingString = "Loading sounds...";

	/* Vector used to keep track of all moving objects (Things). */

	private Vector things;

	/* The main applet's thread. */

	private Thread thread = null;

	/* Initialize the applet. */

	public void init()

	{

		font = new Font("TimesRoman", Font.BOLD, 24);

		fontMetrics = getFontMetrics(font);

		setFont(font);

		resize(WORLD_WIDTH + SCORE_WIDTH, WORLD_HEIGHT);

	}

	/* Start a new game. */

	public synchronized void newGame()

	{

		playing = true;

		clearScreen = true;

		stopThreads();

		thread = new Thread(this);

		thread.start();

	}

	/* The game engine. */

	public void run()

	{

		int n;

		int newBases = 0;

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		score = 0;

		shotCount = 0;

		shots = 0;

		things = new Vector(32);

		missileSemaphore = new Semaphore();

		messageSemaphore = new Semaphore();

		getSounds();

		createBases();

		if (music != null)

		{

			music.loop();

		}

		for (level = 1;; level++)

		{

			if (level == 1)

			{

				if (startSound != null)

				{

					startSound.play();

				}

			}

			else

			{

				if (applauseSound != null)

				{

					applauseSound.play();

				}

			}

			messageSemaphore.take();

			createMessage("Level " + level);

			messageSemaphore.take();

			messageSemaphore.give();

			/* Missile speed. */

			speed = 5 + (level - 1);

			if (speed > (WORLD_HEIGHT / 10))

			{

				speed = WORLD_HEIGHT / 10;

			}

			/* Delay between missiles. */

			delay = 2000 - ((level - 1) * 200);

			if (delay < 500)

			{

				delay = 500;

			}

			/* Number of missiles. */

			missileCount = 5 + ((level - 1) * 5);

			/* Number of shots. */

			shotCount = missileCount * 2;

			/* Wait until missiles are ready. */

			missileSemaphore.take();

			while (missileCount > 0)

			{

				try

				{

					Thread.sleep(delay);

				}

				catch (InterruptedException e)

				{

				}

				/* Fire a missile. */

				createMissile(speed);

				missileCount--;

			}

			/* Wait until missiles are dead. */

			missileSemaphore.take();

			missileSemaphore.give();

			/* See if any new bases can be created. */

			n = score - newBases * POINTS_NEW_BASE;

			while (countBases() < BASES && n >= POINTS_NEW_BASE)

			{

				createBase();

				n -= POINTS_NEW_BASE;

				newBases++;

			}

			/* Game over? */

			n = countBases();

			if (n == 0)

			{

				break;

			}

			else

			{

				score += n * POINTS_BASE;

			}

		}

		playing = false;

		if (music != null)

		{

			music.stop();

		}

		/* Destroy the world! */

		for (int i = 0; i < 5; i++)

		{

			int x = (int) (Math.random() * WORLD_WIDTH);

			int y = (int) (Math.random() * WORLD_HEIGHT);

			things.addElement(new Explosion(this, Color.red, x, y, 500));

		}

		messageSemaphore.take();

		createMessage("GAME OVER");

		messageSemaphore.take();

		messageSemaphore.give();

	}

	/* Stop all running threads. */

	public void stopThreads()

	{

		if (things != null)

		{

			Enumeration e;

			e = things.elements();

			while (e.hasMoreElements())

			{

				Thing thing = (Thing) e.nextElement();

				thing.explode();

			}

		}

	}

	/* Stop this applet. */

	public void stop()

	{

		if (music != null)

		{

			music.stop();

		}

		stopThreads();

		if (thread != null)

		{

			thread.stop();

			thread = null;

		}

	}

	/* Get all the sounds. */

	void getSounds()

	{

		loadingSounds = true;

		repaint();

		startSound = getAudioClip(getCodeBase(), "sounds/sub_dive_horn.au");

		applauseSound = getAudioClip(getCodeBase(), "sounds/applause.au");

		missileSound = getAudioClip(getCodeBase(), "sounds/missile.au");

		shotExplosionSound = getAudioClip(getCodeBase(), "sounds/shot.au");

		missileExplosionSound = getAudioClip(getCodeBase(),
				"sounds/beep_multi.au");

		baseExplosionSound = getAudioClip(getCodeBase(), "sounds/bzzzt.au");

		/*
		 * background music is disabled since the sound file is too
		 * 
		 * large.
		 */

		music = null; /* getAudioClip (getCodeBase (), "sounds/pink_panther.au"); */

		loadingSounds = false;

	}

	/* Create the bases. */

	void createBases()

	{

		for (int i = 0; i < BASES; i++)

		{

			createBase();

		}

	}

	/* Create one base, if possible. */

	void createBase()

	{

		Enumeration e;

		boolean found;

		int i, x;

		for (i = 0, x = BASE_SPACING; i < BASES; i++, x += BASE_WIDTH
				+ BASE_SPACING)

		{

			found = false;

			e = things.elements();

			while (e.hasMoreElements())

			{

				Thing thing = (Thing) e.nextElement();

				if (thing instanceof Base && thing.X == x)

				{

					found = true;

				}

			}

			if (!found)

			{

				things.addElement(new Base(this, Color.blue, x,

				WORLD_HEIGHT - BASE_HEIGHT - 1,

				BASE_WIDTH,

				BASE_HEIGHT));

				break;

			}

		}

	}

	/* Create a shot explosion at x,y. */

	void createShotExplosion(int x, int y)

	{

		if (shots > MAX_SHOTS)

		{

			return;

		}

		things.addElement(new ShotExplosion(this, x, y));

		if (shotExplosionSound != null)

		{

			shotExplosionSound.play();

		}

		shots++;

	}

	/* Create a base explosion at x,y. */

	void createBaseExplosion(int x, int y)

	{

		things.addElement(new BaseExplosion(this, x, y));

		if (baseExplosionSound != null)

		{

			baseExplosionSound.play();

		}

	}

	/* Create a missile starting at x,y with speed. */

	void createMissile(int x1, int y1, int speed)

	{

		int x2, y2;

		Color color = Color.red;

		x2 = (int) (Math.random() * WORLD_WIDTH);

		if (x2 == 0)
			x2 = 1;

		y2 = WORLD_HEIGHT - 2;

		things.addElement(new Missile(this, color, x1, y1, x2, y2, speed));

		if (missileSound != null)

		{

			missileSound.play();

		}

	}

	/* Create a missile at a random location with speed. */

	void createMissile(int speed)

	{

		int x1, y1;

		x1 = (int) (Math.random() * WORLD_WIDTH);

		if (x1 == 0)
			x1 = 1;

		y1 = 2;

		createMissile(x1, y1, speed);

	}

	/* Create a blinking message. */

	void createMessage(String string)

	{

		int w = fontMetrics.stringWidth(string);

		int x = WORLD_WIDTH / 2 - w / 2;

		int y = WORLD_HEIGHT / 2;

		things.addElement(new Message(this, string, x, y));

	}

	/* Handle events. */

	public boolean handleEvent(Event e)

	{

		if (e.id == Event.MOUSE_DOWN) /* mouse click */

		{

			if (playing

			&& shotCount > 0

			&& e.x >= 0 && e.x <= WORLD_WIDTH

			&& e.y >= 0 && e.y <= WORLD_HEIGHT)

			{

				createShotExplosion(e.x, e.y);

				shotCount--;

			}

			else if (!playing)

			{

				newGame();

			}

			return true;

		}

		else

		{

			return super.handleEvent(e);

		}

	}

	/* Check all things for a collision with this missile. */

	public void checkCollision(Missile missile)

	{

		Enumeration e;

		int nbases = 0, nexplosions = 0;

		e = things.elements();

		while (e.hasMoreElements())

		{

			Thing thing = (Thing) e.nextElement();

			if (thing != missile && thing.isAlive())

			{

				if (thing.hit(missile))

				{

					thing.explode();

					if (thing instanceof Base)

					{

						nbases++;

					}

					else if (thing instanceof ShotExplosion)

					{

						nexplosions++;

						score += POINTS_MISSILE;

						if (missileExplosionSound != null)

						{

							missileExplosionSound.play();

						}

					}

				}

			}

		}

		if (nbases > 0)

		{

			createBaseExplosion(missile.X, missile.Y);

		}

		if (nbases > 0 || nexplosions > 0)

		{

			missile.explode();

		}

	}

	/* Update (paint, erase) things. */

	public synchronized void updateThings(Graphics g)

	{

		int i, j;

		Enumeration e;

		e = things.elements();

		while (e.hasMoreElements())

		{

			Thing thing = (Thing) e.nextElement();

			if (thing.isAlive())

			{

				thing.paint(g);

				if (thing instanceof Missile)

				{

					checkCollision((Missile) thing);

				}

			}

			else

			{

				thing.erase(g);

				things.removeElement(thing);

				if (thing instanceof Message)

				{

					messageSemaphore.give();

				}

				else if (thing instanceof ShotExplosion)

				{

					shots--;

				}

			}

		}

	}

	/* Return the number of remaining missiles. */

	public int countMissiles()

	{

		Enumeration e;

		int count = 0;

		e = things.elements();

		while (e.hasMoreElements())

		{

			Thing thing = (Thing) e.nextElement();

			if (thing instanceof Missile && thing.isAlive())

			{

				count++;

			}

		}

		return count;

	}

	/* Return the number of remaining bases. */

	public int countBases()

	{

		Enumeration e;

		int count = 0;

		e = things.elements();

		while (e.hasMoreElements())

		{

			Thing thing = (Thing) e.nextElement();

			if (thing instanceof Base && thing.isAlive())

			{

				count++;

			}

		}

		return count;

	}

	/* Draw the borders. */

	public void updateBorder(Graphics g)

	{

		g.setColor(Color.black);

		g.drawRect(0, 0, WORLD_WIDTH + SCORE_WIDTH - 1, WORLD_HEIGHT - 1);

		g.drawLine(WORLD_WIDTH, 0, WORLD_WIDTH, WORLD_HEIGHT);

	}

	String numberToZeroPaddedString(int number, int length)

	{

		StringBuffer s;

		s = new StringBuffer(Integer.toString(number));

		while (s.length() < length)

		{

			s.insert(0, "0");

		}

		return s.toString();

	}

	/* Draw the score. */

	public synchronized void updateScore(Graphics g)

	{

		int h, w;

		int x, y;

		int n;

		StringBuffer s;

		w = fontMetrics.stringWidth("00000");

		h = fontMetrics.getHeight();

		x = WORLD_WIDTH + 5;

		g.setColor(Color.black);

		y = h;

		g.drawString(scoreString, x, y);

		y += h;

		g.clearRect(x, y - h, w, h);

		g.drawString(numberToZeroPaddedString(score, 5), x, y);

		y += 2 * h;

		g.drawString(shotString, x, y);

		y += h;

		g.clearRect(x, y - h, w, h);

		g.drawString(numberToZeroPaddedString(shotCount, 5), x, y);

	}

	/* Don't clear the screen; call paint. */

	public void update(Graphics g)

	{

		paint(g);

	}

	/* Paint the screen. */

	public void paint(Graphics g)

	{

		if (clearScreen)

		{

			g.setColor(Color.lightGray);

			g.fillRect(0, 0, WORLD_WIDTH + SCORE_WIDTH, WORLD_HEIGHT);

			clearScreen = false;

		}

		if (loadingSounds)

		{

			int w = fontMetrics.stringWidth(loadingString);

			int x = WORLD_WIDTH / 2 - w / 2;

			int y = WORLD_HEIGHT / 2;

			g.setColor(Color.black);

			g.drawString(loadingString, x, y);

			clearScreen = true;

		}

		else if (!playing && (things == null || things.size() == 0))

		{

			int w = fontMetrics.stringWidth(welcomeString);

			int x = WORLD_WIDTH / 2 - w / 2;

			int y = WORLD_HEIGHT / 2;

			g.setColor(Color.black);

			g.drawString(welcomeString, x, y);

		}

		else if (things != null)

		{

			Graphics gc = g.create(0, 0, WORLD_WIDTH, WORLD_HEIGHT);

			updateThings(gc);

			/* No more missiles. */

			if (missileSemaphore != null && countMissiles() == 0
					&& missileCount == 0)

			{

				missileSemaphore.give();

			}

		}

		updateBorder(g);

		updateScore(g);

	}

}

/*
 * 
 * Local variables:
 * 
 * eval: (progn (make-local-variable 'compile-command) (setq compile-command
 * (concat "javac " buffer-file-name)))
 * 
 * End:
 */
