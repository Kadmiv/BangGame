package com.kadmiv.game.repos

enum class Paths(val path: String) {

    // Sounds path
    MAIN_SOUNDS_PATH("sounds/"),

    SOUND_ADDITIONAL_PATH("${MAIN_SOUNDS_PATH.path}additional/"),

    SOUND_BULLET_PATH("${MAIN_SOUNDS_PATH.path}bullet/"),

    SOUND_COMMADNS_PATH("${MAIN_SOUNDS_PATH.path}commands/"),

    SOUND_DEATH_PATH("${MAIN_SOUNDS_PATH.path}death/"),

    // Textures path
    MAIN_TEXTURES_PATH("textures/"),

    TEXTURES_BULLET_PATH("${MAIN_TEXTURES_PATH.path}bullet/"),

    TEXTURES_COMMANDS_PATH("${MAIN_TEXTURES_PATH.path}commands/"),

    TEXTURES_HERO_PATH("${MAIN_TEXTURES_PATH.path}hero/"),

    TEXTURES_DEATH_PATH("${TEXTURES_HERO_PATH.path}death/"),

    TEXTURES_DO_ELSE_PATH("${TEXTURES_HERO_PATH.path}do_else/"),

    TEXTURES_WIN_PATH("${TEXTURES_HERO_PATH.path}win/");
}
