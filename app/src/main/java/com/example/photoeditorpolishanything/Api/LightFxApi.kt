package com.example.photoeditorpolishanything.Api

import com.google.gson.annotations.SerializedName

data class LightFxApi(

	@field:SerializedName("baseUrl")
	val baseUrl: String?,

	@field:SerializedName("data")
	val data: List<DataItem?>?
)

data class Water(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class GoldenHour(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Sunlight(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Moon(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Retros(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Bokeh(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Explosion(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Fantasy(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class FogOverlay(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Neons(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Bubble(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Scarf(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Smoke(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Rain(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Lightning(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Snow(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class LightStroke(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class DreamyLight(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Groupes(

	var textCategory: String?,

	@field:SerializedName("subImageUrl")
	val subImageUrl: List<String?>?,

	@field:SerializedName("mainImageUrl")
	val mainImageUrl: List<String?>?
)

data class Textures(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Lovees(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class DustAndSunlight(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Shadow(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class DataItem(

	@field:SerializedName("Petals")
	val petals: Petals?,

	@field:SerializedName("Water")
	val water: Water?,

	@field:SerializedName("Galaxy")
	val galaxy: Galaxy?,

	@field:SerializedName("Snow")
	val snow: Snow?,

	@field:SerializedName("Moon")
	val moon: Moon?,

	@field:SerializedName("Rose")
	val rose: Rose?,

	@field:SerializedName("Shadow")
	val shadow: Shadow?,

	@field:SerializedName("Shutter")
	val shutter: Shutter?,

	@field:SerializedName("Smoke")
	val smoke: Smoke?,

	@field:SerializedName("Retro")
	val retro: Retros?,

	@field:SerializedName("Texture")
	val texture: Textures?,

	@field:SerializedName("Bubble")
	val bubble: Bubble?,

	@field:SerializedName("Fog Overlay")
	val fogOverlay: FogOverlay?,

	@field:SerializedName("Neon")
	val neon: Neons?,

	@field:SerializedName("Rain")
	val rain: Rain?,

	@field:SerializedName("Star")
	val star: Star?,

	@field:SerializedName("Rainbow")
	val rainbow: Rainbow?,

	@field:SerializedName("Bokeh")
	val bokeh: Bokeh?,

	@field:SerializedName("Fireworks")
	val fireworks: Fireworks?,

	@field:SerializedName("Dust and Scratch")
	val dustAndScratch: DustAndScratch?,

	@field:SerializedName("Broken Glass")
	val brokenGlass: BrokenGlass?,

	@field:SerializedName("Explosion")
	val explosion: Explosion?,

	@field:SerializedName("Golden hour")
	val goldenHour: GoldenHour?,

	@field:SerializedName("Autumn")
	val autumn: Autumnes?,

	@field:SerializedName("Film Leak")
	val filmLeak: FilmLeak?,

	@field:SerializedName("Scarf")
	val scarf: Scarf?,

	@field:SerializedName("Diamond")
	val diamond: Diamond?,

	@field:SerializedName("Confetti")
	val confetti: Confetti?,

	@field:SerializedName("Light Stroke")
	val lightStroke: LightStroke?,

	@field:SerializedName("Dreamy light")
	val dreamyLight: DreamyLight?,

	@field:SerializedName("Lightning")
	val lightning: Lightning?,

	@field:SerializedName("Love")
	val love: Lovees?,

	@field:SerializedName("Fire")
	val fire: Fire?,

	@field:SerializedName("Face Shadow")
	val faceShadow: FaceShadow?,

	@field:SerializedName("Fantasy")
	val fantasy: Fantasy?,

	@field:SerializedName("Sunlight")
	val sunlight: Sunlight?,

	@field:SerializedName("Ripple")
	val ripple: Ripple?,

	@field:SerializedName("Prism")
	val prism: Prism?,

	@field:SerializedName("Dust and Sunlight")
	val dustAndSunlight: DustAndSunlight?,

	@field:SerializedName("Flurries")
	val flurries: Flurries?
)

data class Prism(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Petals(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Galaxy(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Rainbow(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Star(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class BrokenGlass(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Fire(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Flurries(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Rose(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Ripple(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Shutter(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Diamond(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Fireworks(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class FilmLeak(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Autumnes(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class FaceShadow(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class DustAndScratch(

	@field:SerializedName("Group")
	val group: Groupes?
)

data class Confetti(

	@field:SerializedName("Group")
	val group: Groupes?
)
