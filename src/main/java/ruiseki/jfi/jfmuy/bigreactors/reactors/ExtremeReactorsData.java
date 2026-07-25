package ruiseki.jfi.jfmuy.bigreactors.reactors;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import erogenousbeef.bigreactors.api.data.CoilPartData;
import erogenousbeef.bigreactors.api.data.ReactorInteriorData;
import erogenousbeef.bigreactors.api.data.ReactorReaction;
import erogenousbeef.bigreactors.api.data.SourceProductMapping;
import erogenousbeef.bigreactors.api.registry.Reactants;
import erogenousbeef.bigreactors.api.registry.ReactorConversions;
import erogenousbeef.bigreactors.api.registry.ReactorInterior;
import erogenousbeef.bigreactors.api.registry.TurbineCoil;

public class ExtremeReactorsData {

    private ExtremeReactorsData() {}

    public static final ImmutableMap<String, List<SourceProductMapping>> Reactants_reactantToSolid = getImmutableMapping(
        Reactants.class,
        "_reactantToSolid");

    public static final ImmutableMap<String, ReactorReaction> ReactorConversions_reactions = getImmutableMapping(
        ReactorConversions.class,
        "_reactions");

    public static final ImmutableMap<String, ReactorInteriorData> ReactorInterior_reactorModeratorBlocks = getImmutableMapping(
        ReactorInterior.class,
        "_reactorModeratorBlocks");

    public static final ImmutableMap<String, ReactorInteriorData> ReactorInterior_reactorModeratorFluids = getImmutableMapping(
        ReactorInterior.class,
        "_reactorModeratorFluids");

    public static final ImmutableMap<String, CoilPartData> TurbineCoil_blocks = getImmutableMapping(
        TurbineCoil.class,
        "_blocks");

    @SuppressWarnings("unchecked")
    private static <K, V> Map<K, V> getMapping(Class<?> mappingClass, String mappingName) {
        try {
            Field mappingField = mappingClass.getDeclaredField(mappingName);
            mappingField.setAccessible(true);

            Object value = mappingField.get(null);
            if (value == null) {
                return Map.of();
            }

            return (Map<K, V>) value;
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(
                "Field '" + mappingName + "' not found in " + mappingClass.getName() + ". Check mod version.",
                e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(
                "Unable to access field '" + mappingName + "' in " + mappingClass.getName(),
                e);
        }
    }

    private static <K, V> ImmutableMap<K, V> getImmutableMapping(Class<?> mappingClass, String mappingName) {
        return ImmutableMap.copyOf(getMapping(mappingClass, mappingName));
    }
}
