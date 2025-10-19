package dev.floraf.loader.data;

/**
 * A representation of one out of of the three types of dependency relations
 * one patch may have with another.
 */
public enum DependencyType {
    /**
     * Also called a "soft dependency". Mashup will attempt to load this patch,
     * regardless of whether the dependency specified is present. However, if
     * the resource this patch depends on is present, it will be loaded after
     * the specified patch.
     * 
     * Note that this dependency type does not allow game version or DLC
     * checks (unlike the other two types), as the game's DLC are patched in
     * prior to mod-loading and the former does not make much sense.
     * 
     * Use of this dependency type is often to resolve mod conflicts, where
     * the ordering of mods loaded may impact the targets of the mods' patches.
     */
    AWAIT,
    /**
     * Also called a "firm dependency". Mashup will not load this patch if the
     * resource it depends on is not present. It is important to note that
     * if another patch REQUIREs (see below) a patch that has a firm
     * dependency, unsatisfied dependencies could cascade into a mod-loader
     * halt.
     * 
     * Use of this dependency type is often for implementing integrations into
     * other mods or DLC that are optional in nature.
     */
    EXPECT,
    /**
     * Also called a "hard dependency". Mashup will halt the mod-loading
     * process if the resource that it depends on is not present.
     * 
     * This is a very extreme measure, so use hard dependencies with caution!
     * Only use this dependency type if the _entire mod_ no longer makes sense
     * being present if the dependency isn't either.
     */
    REQUIRE,
}