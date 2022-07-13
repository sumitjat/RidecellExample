package com.example.ridecellexample.data.model

data class Characters(val info: Info, val results: List<CharacterData>)

data class Info(val count: Int?, val pages: String?, val next: String?, val prev: String?)
