package br.com.lunaticmc.scoreboard.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class ScoreboardModel {

    private String world;

    private String name;

    private ArrayList<String> texts;
    private ArrayList<String> path;

}
