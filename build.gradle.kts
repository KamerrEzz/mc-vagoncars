plugins {
	// Applies the correct Loom variant (obfuscated/remap vs. unobfuscated) based on the active version.
	id("dev.kikugie.loom-back-compat")
	id("maven-publish")
}

// DO NOT set group here — it comes from stonecutter.properties.toml via loomx.
version = "${property("mod.version")}+${sc.current.version}"
group = property("mod.group") as String
base.archivesName.set(property("mod.id") as String)

val requiredJava: JavaVersion = when {
	sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
	else -> JavaVersion.VERSION_21
}

loom {
	splitEnvironmentSourceSets()

	mods {
		create("minecarts") {
			sourceSet(sourceSets["main"])
			sourceSet(sourceSets["client"])
		}
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${sc.current.version}")
	// Applies Mojang Mappings — a no-op on unobfuscated (26.1+) versions, where none are needed.
	loomx.applyMojangMappings()

	// Use `mod{ConfigurationName}` even on unobfuscated versions — loom-back-compat aliases them.
	modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	withSourcesJar()

	sourceCompatibility = requiredJava
	targetCompatibility = requiredJava

	toolchain {
		languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
	}
}

tasks {
	processResources {
		fun MutableMap<String, String>.register(key: String, prop: String) {
			val value: String = sc.properties[prop]
			inputs.property(key, value)
			set(key, value)
		}

		val props = buildMap {
			register("version", "mod.version")
			register("minecraft", "mod.mc_compat")
			register("java_compat", "mod.java_compat")
		}

		filesMatching("fabric.mod.json") { expand(props) }

		val mixinJava = "JAVA_${requiredJava.majorVersion}"
		filesMatching("*.mixins.json") { expand("java" to mixinJava) }
	}

	jar {
		val projectName = rootProject.name
		inputs.property("projectName", projectName)

		from("LICENSE") {
			rename { "${it}_$projectName" }
		}
	}

	register<Copy>("buildAndCollect") {
		group = "build"
		description = "Builds mod jars and copies results to build/libs/{mod version}/"

		inputs.property("version", project.property("mod.version"))
		from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
		into(rootProject.layout.buildDirectory.dir("libs/${project.property("mod.version")}"))
	}
}

// configure the maven publication
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
