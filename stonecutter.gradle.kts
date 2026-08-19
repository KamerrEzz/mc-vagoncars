plugins {
	id("dev.kikugie.stonecutter")
}

stonecutter active "26.2.x"

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
	swaps["mod_version"] = "\"${property("mod.version")}\";"
	swaps["minecraft"] = "\"${node.metadata.version}\";"
	dependencies["fapi"] = node.project.property("deps.fabric_api") as String

	// Pure 1:1 renames between 1.21.1 (Yarn-era Mojang mappings) and 26.1+ (native
	// Mojang mappings) — confirmed against the real remapped 1.21.1 jars, not guessed.
	replacements {
		string(current.parsed < "26") {
			replace("Identifier", "ResourceLocation")
			replace("isLocalInstanceAuthoritative", "isControlledByLocalInstance")
			replace("snapTo", "moveTo")
			replace("SUCCESS_SERVER", "SUCCESS")
		}
	}
}
