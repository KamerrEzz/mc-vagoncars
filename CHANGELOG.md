# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.3.0] - 2026-08-18

### Added
- Multi-version support via Stonecutter — the mod now also builds and ships for Minecraft 1.21.1, alongside the existing 26.2 target

## [0.2.2] - 2026-08-18

### Fixed
- Carrito can now climb one-block ledges instead of only being able to descend them
- Carrito no longer jitters for other players in multiplayer — clients that aren't driving it now interpolate the server position instead of running their own physics

## [0.2.1] - 2026-08-12

### Fixed
- Carrito's model no longer renders about a block below its hitbox and facing the wrong way
- Wheels now spin the correct way for the direction of travel

## [0.2.0] - 2026-08-12

### Added
- Carrito now renders with its Blockbench-modeled body and four wheels, replacing the invisible placeholder
- Wheels spin continuously based on driving speed

## [0.1.0] - 2026-08-12

### Added
- Initial Fabric mod scaffold for Minecraft 26.2, including the "Drill" (`taladro_carrito`) item with model, texture, and language entry
- Carrito, a rail-free vehicle entity you can mount and drive freely with WASD

[Unreleased]: https://github.com/KamerrEzz/mc-vagoncars/compare/v0.3.0...HEAD
[0.3.0]: https://github.com/KamerrEzz/mc-vagoncars/compare/v0.2.2...v0.3.0
[0.2.2]: https://github.com/KamerrEzz/mc-vagoncars/compare/v0.2.1...v0.2.2
[0.2.1]: https://github.com/KamerrEzz/mc-vagoncars/compare/v0.2.0...v0.2.1
[0.2.0]: https://github.com/KamerrEzz/mc-vagoncars/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/KamerrEzz/mc-vagoncars/releases/tag/v0.1.0
