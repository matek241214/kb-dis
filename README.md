# Knockback Displacement (Fabric 1.21.11)

Mod zamienia vanillowy knockback (zmiana velocity) na bezpośrednie przesunięcie trafionej istoty o stały dystans, z respektowaniem kolizji.

## Budowanie

Wymagania: JDK 21.

```
gradle build        # lub ./gradlew build, jeśli skopiujesz wrapper z szablonu Fabric
```

Gotowy plik jest w `build/libs/knockback-displacement-1.0.0.jar`. Wrzuć go do `mods/` razem z Fabric API.

## GUI

W grze wpisz `/kbd`, otworzy się ekran z suwakami mocy i podniesienia oraz przyciskiem włącz/wyłącz. Zmiany zapisują się po kliknięciu Gotowe. Działa w singleplayerze i na własnym serwerze/LAN; na cudzym serwerze liczy się config serwera.

## Jar bez instalowania niczego

Wrzuć folder na GitHub (publiczne lub prywatne repo). Zakładka Actions -> build zbuduje jar do pobrania w Artifacts.

## Config

`config/knockback-displacement.json` (tworzy się przy pierwszym uruchomieniu):

- `enabled` - włącza/wyłącza mod
- `blocksPerStrength` - bloki przesunięcia na 1.0 siły (vanilla siła to 0.4, więc domyślnie ok. 1.2 bloku)
- `verticalLift` - dodatkowe przesunięcie w górę
- `respectKnockbackResistance` - czy resistance zmniejsza dystans
- `affectPlayers` / `affectMobs` - na kogo działa
- `maxDistance` - limit dystansu jednego przesunięcia
