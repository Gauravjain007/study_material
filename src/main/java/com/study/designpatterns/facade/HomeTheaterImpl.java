package com.study.designpatterns.facade;

// Subsystem classes for home theater components
class Amplifier {
    private String description;

    public Amplifier(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void setVolume(int level) {
        System.out.println(description + " setting volume to " + level);
    }

    public void setSurroundSound() {
        System.out.println(description + " surround sound on (5.1)");
    }
}

class DvdPlayer {
    private String description;

    public DvdPlayer(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void play(String movie) {
        System.out.println(description + " playing \"" + movie + "\"");
    }

    public void stop() {
        System.out.println(description + " stopped");
    }

    public void eject() {
        System.out.println(description + " eject");
    }
}

class Projector {
    private String description;

    public Projector(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void wideScreenMode() {
        System.out.println(description + " in widescreen mode (16x9 aspect ratio)");
    }
}

class Screen {
    private String description;

    public Screen(String description) {
        this.description = description;
    }

    public void up() {
        System.out.println(description + " going up");
    }

    public void down() {
        System.out.println(description + " going down");
    }
}

class PopcornPopper {
    private String description;

    public PopcornPopper(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void pop() {
        System.out.println(description + " popping popcorn!");
    }
}

class TheaterLights {
    private String description;

    public TheaterLights(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + " on");
    }

    public void off() {
        System.out.println(description + " off");
    }

    public void dim(int level) {
        System.out.println(description + " dimming to " + level + "%");
    }
}

// Facade class that simplifies the home theater operations
class HomeTheaterFacade {
    private Amplifier amp;
    private DvdPlayer dvd;
    private Projector projector;
    private Screen screen;
    private PopcornPopper popper;
    private TheaterLights lights;

    public HomeTheaterFacade(Amplifier amp, DvdPlayer dvd,
            Projector projector, Screen screen,
            PopcornPopper popper, TheaterLights lights) {
        this.amp = amp;
        this.dvd = dvd;
        this.projector = projector;
        this.screen = screen;
        this.popper = popper;
        this.lights = lights;
    }

    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        popper.on();
        popper.pop();
        lights.dim(10);
        screen.down();
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setVolume(5);
        amp.setSurroundSound();
        dvd.on();
        dvd.play(movie);
        System.out.println("Movie experience started!\n");
    }

    public void endMovie() {
        System.out.println("Shutting movie theater down...");
        popper.off();
        lights.on();
        screen.up();
        projector.off();
        amp.off();
        dvd.stop();
        dvd.eject();
        dvd.off();
        System.out.println("Movie theater is now off.\n");
    }

    // Additional convenience methods
    public void listenToMusic(String album) {
        System.out.println("Get ready for some music...");
        lights.dim(30);
        amp.on();
        amp.setVolume(7);
        dvd.on();
        dvd.play(album);
        System.out.println("Music experience started!\n");
    }
}

// Client code
public class HomeTheaterImpl {
    public static void main(String[] args) {
        // Create all the components
        Amplifier amp = new Amplifier("Top-O-Line Amplifier");
        DvdPlayer dvd = new DvdPlayer("Top-O-Line DVD Player");
        Projector projector = new Projector("Top-O-Line Projector");
        Screen screen = new Screen("Theater Screen");
        PopcornPopper popper = new PopcornPopper("Popcorn Popper");
        TheaterLights lights = new TheaterLights("Theater Ceiling Lights");

        // Create the facade
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(
                amp, dvd, projector, screen, popper, lights);

        // Use the simplified interface
        homeTheater.watchMovie("Raiders of the Lost Ark");
        homeTheater.endMovie();

        homeTheater.listenToMusic("The Dark Side of the Moon");
    }
}
