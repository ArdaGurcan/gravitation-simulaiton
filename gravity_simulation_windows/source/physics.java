import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import interfascia.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class physics extends PApplet {



GUIController c;
IFTextField t1;
IFTextField t2;
String planet1 = "mars";
String planet2 = "earth";
String title = "GRAVITY SIMULATION";
String names = "Made by Arda Gürcan and Kutay Erk Güven";
String hotkey1 = "Press F1 to switch between gravity values. Press F2 to hide all text. Press F3 to reset planets.";
String planets = "Planets: Mercury, Venus, Earth, Moon, Mars, Pluto";
PVector pos1;
PVector v1 = new PVector(0, 0);
PVector a1 = new PVector(0, 0);
float r1;
float m1;
int c1;
PImage i1;

PVector pos2;
PVector v2 = new PVector(0, 0);
PVector a2 = new PVector(0, 0);
float r2;
float m2;
int c2;
PImage i2;

float offset;

float G1 = 60;
float G2 = 6.67408e-11f;
float G = G1;
PVector[] positions = new PVector[100]; 
boolean showText = true;

public void setup()
{
    for (int i = 0; i < positions.length; i++)
    {
        positions[i] = new PVector(random(width), random(height));
    }
    PFont font = loadFont("Helvetica-60.vlw"); 
    textFont(font, 60);
    
    SetPlanetValues(planet1, planet2);
    c = new GUIController(this);
    t1 = new IFTextField("Text Field", 25, 100, 150);
    t2 = new IFTextField("Text Field", width - 175, 100, 150);
    
    c.add(t1);
    c.add(t2);
    t1.addActionListener(this);
    t2.addActionListener(this);

    
    
    pos1 = new PVector(r1, height/2);
    pos2 = new PVector(width - r2, height/2);
}

public void actionPerformed(GUIEvent e) 
{
    if (e.getMessage().equals("Completed"))
    {

        planet1 = t1.getValue();
        planet2 = t2.getValue();
        println(t2.getValue() + "a");
       
        SetPlanetValues(planet1, planet2);
        pos1 = new PVector(r1, height/2);
        pos2 = new PVector(width - r2, height/2);
        v1 = new PVector(0, 0);
        a1 = new PVector(0, 0);
        v2 = new PVector(0, 0);
        a2 = new PVector(0, 0);
    }
}


public void SetPlanetValues(String p1, String p2) {
    
    
    switch(p1.toLowerCase())
    {    

        case "mercury":
            i1 = loadImage("mercury.png");
            m1 = 3.285f;
            r1 = 24.39f;
            c1 = color(231, 139, 98);
            break;
        case "venus":
            i1 = loadImage("venus.png");
            m1 = 48.6732f;
            r1 = 60.51f;
            c1 = color(205, 62, 68);
            break;
        case "earth":
            i1 = loadImage("earth.png");
            m1 = 59.72f;
            r1 = 53.71f;
            c1 = color(137, 208, 240);
            break;
        case "mars":
            i1 = loadImage("mars.png");
            m1 = 6.39f;
            r1 = 33.895f;
            c1 = color(227, 119, 84);
            break;
        case "moon":
            i1 = loadImage("moon.png");
            m1 = 0.734767309f;
            r1 = 17.371f;
            c1 = color(255, 251, 217);
            break;
        case "pluto":
            i1 = loadImage("pluto.png");
            r1 = 11.883f;
            m1 = 0.130900f;
            c1 = color(214, 200, 186);
            break;
    }
    
    switch(p2.toLowerCase())
    {

        case "mercury":
            i2 = loadImage("mercury.png");
            m2 = 3.285f;
            r2 = 24.39f;
            c2 = color(231, 139, 98);
            break;
        case "venus":
            i2 = loadImage("venus.png");
            m2 = 48.6732f;
            r2 = 60.51f;
            c2 = color(205, 62, 68);
            break;
        case "earth":
            i2 = loadImage("earth.png");
            m2 = 59.72f;
            r2 = 53.71f;
            c2 = color(137, 208, 240);
            break;
        case "mars":
            i2 = loadImage("mars.png");
            m2 = 6.39f;
            r2 = 33.895f;
            c2 = color(227, 119, 84);
            break;
        case "moon":
            i2 = loadImage("moon.png");
            m2 = 0.734767309f;
            r2 = 17.371f;
            c2 = color(255, 251, 217);
            break;
        case "pluto":
            i2 = loadImage("pluto.png");
            r2 = 11.883f;
            m2 = 0.130900f;
            c2 = color(214, 200, 186);
            break;
    }
    offset = (r1 + r2) / 33;
}


public void keyPressed()
{
    switch(keyCode)
    {
        case 112:
            if (G == G1)
            {
                G = G2;
            }
            else 
            {
                G = G1;
            }
            pos1 = new PVector(r1, height / 2);
            pos2 = new PVector(width - r2, height / 2);
            v1 = new PVector(0, 0);
            a1 = new PVector(0, 0);
            v2 = new PVector(0, 0);
            a2 = new PVector(0, 0);
            break;
            
        case 113:
            showText = !showText;
            break;
            
        case 114:
            pos1 = new PVector(r1, height / 2);
            pos2 = new PVector(width - r2, height / 2);
            v1 = new PVector(0, 0);
            a1 = new PVector(0, 0);
            v2 = new PVector(0, 0);
            a2 = new PVector(0, 0);
            break;
    }
}

public void draw() {
    background(0);
    for (int i = 2; i < positions.length+2; i++)
    {
        fill(color(255));
        randomSeed(i * i * i);
        int r = (int)random(1, 5);
        ellipse(positions[i - 2].x, positions[i - 2].y, r, r);
    }


    if (abs(pos1.x-pos2.x) <= r1 + r2 +offset)
    {

        float vf1 = (v1.x * ((m1 - m2) / (m1 + m2))) + (v2.x * ((2 * m2) / (m1 + m2)));
        float vf2 = (v1.x * ((2 * m1)/(m1 + m2)))-(v2.x * ((m1 - m2) / (m1 + m2)));

        v1.x = vf1 * 0.95f;
        v2.x = vf2 * 0.95f;
    }
    else
    {
        a1 = new PVector((G * m2) / (dist(pos1.x, pos1.y, pos2.x, pos2.y) * (dist(pos1.x, pos1.y, pos2.x, pos2.y) + offset)), 0);
        v1 = v1.add(a1);

        a2 = new PVector((-G * m1) / (dist(pos1.x, pos1.y, pos2.x, pos2.y) * (dist(pos1.x, pos1.y, pos2.x, pos2.y) + offset)), 0);
        v2 = v2.add(a2);
    }

    //object 1
    if (pos1.x > width || pos1.x < 0)
    {
        v1.x *= -1;
    }

    pos1 = pos1.add(v1);
    stroke(0);
    fill(c1);
    image(i1,pos1.x-r1,pos1.y-r1,r1*2,r1*2);
    //ellipse(pos1.x, pos1.y, r1 * 2, r1 * 2);

    if (pos2.x > width || pos2.x < 0)
    {
        v2.x *= -1;
    }
    
    //object 2
    pos2 = pos2.add(v2);
    stroke(0);
    fill(c2);
    image(i2,pos2.x-r2,pos2.y-r2,r2*2,r2*2);
    //ellipse(pos2.x, pos2.y, r2 * 2, r2 * 2);
    color(c1);

    //ui
    if (showText == true) {
        fill(0);
        rect(0, 510, width, height);
        rect(0, 0, width, 150);
        fill(color(255, 255, 255));
        textSize(64);
        text(title, 50, 70);
        textSize(16);
        text(names, 250, 90);
        text(hotkey1, 55, 550);
        text(planets, 210, 570);
        t1.setPosition(25, 100);
        t2.setPosition(width-175, 100);
    } 
    else
    {
        t1.setPosition(900, 900);
        t2.setPosition(900, 900);
    }
}
    public void settings() {  size(800, 600);  smooth(); }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "physics" };
        if (passedArgs != null) {
          PApplet.main(concat(appletArgs, passedArgs));
        } else {
          PApplet.main(appletArgs);
        }
    }
}
