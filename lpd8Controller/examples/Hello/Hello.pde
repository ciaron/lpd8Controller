import lpd8Controller.*;

LPD8Controller hello;

void setup() {
  size(400,400);
  smooth();
  
  hello = new LPD8Controller(this);
  
  PFont font = createFont("",40);
  textFont(font);
}

void draw() {
  background(0);
  fill(255);
  text(hello.sayHello(), 40, 200);
}