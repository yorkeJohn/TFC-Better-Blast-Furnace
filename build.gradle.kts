plugins {
    id("net.neoforged.moddev") version "2.0.107"
}

// Toolchain versions
val minecraftVersion: String = "1.21.1"
val neoForgeVersion: String = "21.1.197"
val parchmentVersion: String = "2024.11.17"
val parchmentMinecraftVersion: String = "1.21.1"

// Dependency versions
val patchouliVersion: String = "1.21.1-92-NEOFORGE"

val modId: String = "tfcbetterbf"
val modVersion: String = System.getenv("GITHUB_REF_NAME") ?: "0.0.0-dev"
val javaVersion: String = "21"

base {
    archivesName.set("TFCBetterBlastFurnace-$minecraftVersion")
    group = "com.tadashi"
    version = modVersion
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
}

repositories {
    mavenCentral()
    mavenLocal()
    exclusiveContent {
        forRepository { maven("https://maven.blamejared.com/") }
        filter { includeGroup("vazkii.patchouli") }
    }
    exclusiveContent {
        forRepository { maven("https://www.cursemaven.com") }
        filter { includeGroup("curse.maven") }
    }
}

neoForge {
    version = neoForgeVersion

    parchment {
        minecraftVersion.set(parchmentMinecraftVersion)
        mappingsVersion.set(parchmentVersion)
    }

    runs {
        configureEach {
            jvmArguments.addAll("-XX:+IgnoreUnrecognizedVMOptions", "-XX:+AllowEnhancedClassRedefinition", "-ea")
        }
        register("client") {
            client()
            gameDirectory = file("run/client")
        }
        register("server") {
            server()
            gameDirectory = file("run/server")
            programArgument("--nogui")
        }
    }

    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    // TerraFirmaCraft
    implementation("curse.maven:tfc-302973:8249859")

    // Patchouli
    implementation("vazkii.patchouli:Patchouli:$patchouliVersion")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("META-INF/neoforge.mods.toml") {
            expand("version" to project.version)
        }
    }

    jar {
        manifest {
            attributes["Specification-Title"] = "TFC Better Blast Furnace"
            attributes["Specification-Vendor"] = "tadashi_3"
            attributes["Specification-Version"] = "1"
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = project.version
            attributes["Implementation-Vendor"] = "tadashi_3"
        }
    }
}