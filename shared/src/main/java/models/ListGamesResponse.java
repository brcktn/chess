package models;

import java.util.Collection;

public record ListGamesResponse(Collection<GameData> games) {}
