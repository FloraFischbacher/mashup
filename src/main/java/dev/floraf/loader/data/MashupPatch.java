package dev.floraf.loader.data;

/**
 * A representation of a patch identifier for a Mashup mod, displayed in string
 * form as "mod_name:patch_name".
 */
public class MashupPatch {
    /**
     * The name of the mod that this patch corresponds to.
     */
    String mod;

    /**
     * The name of the patch itself.
     */
    String patch;

    /**
     * Basic constructor for representing one patch within a Mashup mod.
     * Does not verify whether the patch already exists; this is the job of
     * {@link DependencyGraph}.
     *
     * @param mod The name of the mod that this patch is for.
     * @param patch The name of the patch.
     * @throws IllegalArgumentException In the event that either identifier
     *         provided to this constructor is invalid.
     */
    public MashupPatch(String mod, String patch)
    throws IllegalArgumentException {
        if (!isIdentifier(mod) || !isIdentifier(patch)) {
            String msg = String.format(
                "Mod identifier not valid: \"%s\"!", mod + ":" + patch);
            throw new IllegalArgumentException(msg);
        }

        this.mod = mod;
        this.patch = patch;
    }

    /**
     * Alternative named constructor used to create an object instance out
     * of a string representation.
     * @param idString The string to use when attempting to create the object.
     * @return A new instance of {@link MashupPatch} based on the contents of
     *         the provided string.
     * @throws IllegalArgumentException In the event that <pre>idString</pre>
     *         is not a valid patch identifier.
     * @see MashupPatch#isIdentifier(String)
     */
    public static MashupPatch parseString(String idString)
    throws IllegalArgumentException {
        String msg = String.format(
            "Mod identifier not valid: \"%s\"!", idString);

        String[] parts = idString.split(":");
        if (parts.length != 2)
            throw new IllegalArgumentException(msg);

        for (String part : parts) {
            if (!isIdentifier(part)) throw new IllegalArgumentException(msg);
        }

        return new MashupPatch(parts[0], parts[1]);
    }

    /**
     * Utility method used to verify whether a segment of text is a valid
     * Mashup identifier (i.e. conformant to the regular expression
     * "[A-Za-z_][A-Za-z0-9_]*").
     * @param check The string to check.
     * @return Whether the provided string is a valid Mashup identifier.
     */
    public static boolean isIdentifier(String check) {
        if (check == null || check.isEmpty()) return false;
        char[] chars = check.toCharArray();

        char first = chars[0];
        if (!Character.isAlphabetic(first) || !(first == '_'))
            return false;

        for (int i = 1; i < chars.length; i++) {
            char current = chars[i];
            if (!Character.isAlphabetic(current)
                || !Character.isDigit(current)
                || !(current == '_')) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return this.mod + ":" + this.patch;
    }
}
