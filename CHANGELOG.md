# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- API Post joueurs
- Tests unitaires
- Front

### Changed

- Refonte de l'architecture des repositories (un seul repository par usecase dans l'architecture hexagonale/clean)

### Researched

- Déviation de l'algorithme avec la répartition mathématique parfaite

## [0.1.0] - 2024-11-22

### Added

- Optimiser l'algorithme d'appariement des joueurs pour que les joueurs arrivés en cours de route ne se retrouvent pas
  tous dans le même groupe
- Ajouter le pseudonyme d'un joueur dans tous les objets joueurs

### Changed

- Refonte de l'architecture des repositories (un seul repository par usecase dans l'architecture hexagonale/clean)

## [0.0.1] - 2024-04-21

### Added

- API Bulk POST joueurs
- API PATCH joueurs
- API GET joueurs
- API POST parties
- API PATCH parties
- API GET parties
- API GET groupes-optimises

[0.0.1]: https://github.com/Borneheld/la-meute-tournoi/releases/tag/v0.0.1
