package org.confluence.mod.util;

public final class CuriosUtils {
//    public static boolean noSameCurio(LivingEntity living, Class<?> clazz) {
//        return noSameCurio(living, (Predicate<ItemStack>) itemStack -> clazz.isInstance(itemStack.getItem()));
//    }
//
//    public static boolean noSameCurio(LivingEntity living, Predicate<ItemStack> predicate) {
//        AtomicBoolean isEmpty = new AtomicBoolean(true);
//        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
//            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
//                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
//                for (int i = 0; i < stackHandler.getSlots(); i++) {
//                    ItemStack stack = stackHandler.getStackInSlot(i);
//                    if (!stack.isEmpty() && predicate.test(stack)) {
//                        isEmpty.set(false);
//                        return;
//                    }
//                }
//            }
//        });
//        return isEmpty.get();
//    }
//
//    public static <C extends Item & ICurioItem> boolean noSameCurio(LivingEntity living, C curio) {
//        return noSameCurio(living, (Predicate<ItemStack>) itemStack -> itemStack.getItem() == curio);
//    }
//
//    public static boolean hasCurio(LivingEntity living, Class<?> clazz) {
//        return !noSameCurio(living, clazz);
//    }
//
//    public static boolean hasCurio(LivingEntity living, Predicate<ItemStack> predicate) {
//        return !noSameCurio(living, predicate);
//    }
//
//    public static <C extends Item & ICurioItem> boolean hasCurio(LivingEntity living, C curio) {
//        return !noSameCurio(living, curio);
//    }
//
//    public static <C> double calculateValue(LivingEntity living, Class<C> clazz, Function<C, Number> function, double baseValue) {
//        AtomicDouble atomic = new AtomicDouble(baseValue);
//        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
//            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
//                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
//                for (int i = 0; i < stackHandler.getSlots(); i++) {
//                    ItemStack stack = stackHandler.getStackInSlot(i);
//                    Item item = stack.getItem();
//                    if (!stack.isEmpty() && clazz.isInstance(item)) {
//                        atomic.addAndGet(function.apply(clazz.cast(item)).doubleValue());
//                    }
//                }
//            }
//        });
//        return atomic.get();
//    }
//
//    public static <C> Optional<C> findCurio(LivingEntity living, Class<C> clazz) {
//        AtomicReference<Optional<C>> atomic = new AtomicReference<>(Optional.empty());
//        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
//            Optional<SlotResult> results = handler.findFirstCurio(itemStack -> clazz.isInstance(itemStack.getItem()));
//            if (results.isEmpty()) return;
//            atomic.set(Optional.of(clazz.cast(results.get().stack().getItem())));
//        });
//        return atomic.get();
//    }
//
//    public static <C extends Item & ICurioItem> Optional<ItemStack> findCurio(LivingEntity living, C curio) {
//        AtomicReference<Optional<ItemStack>> atomic = new AtomicReference<>(Optional.empty());
//        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
//            Optional<SlotResult> results = handler.findFirstCurio(itemStack -> itemStack.getItem() == curio);
//            if (results.isEmpty()) return;
//            atomic.set(Optional.of(results.get().stack()));
//        });
//        return atomic.get();
//    }
//
//    public static <C extends Item & ICurioItem> Optional<ItemStack> findCurioAt(LivingEntity living, C curio, String id) {
//        AtomicReference<Optional<ItemStack>> atomic = new AtomicReference<>(Optional.empty());
//        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
//            Map<String, ICurioStacksHandler> curios = handler.getCurios();
//            ICurioStacksHandler stacksHandler = curios.get(id);
//            if (stacksHandler != null) {
//                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
//                for (int i = 0; i < stackHandler.getSlots(); i++) {
//                    ItemStack stack = stackHandler.getStackInSlot(i);
//                    if (!stack.isEmpty() && stack.getItem() == curio) {
//                        atomic.set(Optional.of(stack));
//                        return;
//                    }
//                }
//            }
//        });
//        return atomic.get();
//    }
//
//    public static ArrayList<ItemStack> getCurios(LivingEntity living) {
//        ArrayList<ItemStack> items = new ArrayList<>();
//        CuriosApi.getCuriosInventory(living).ifPresent(curiosItemHandler -> {
//            IItemHandlerModifiable itemHandlerModifiable = curiosItemHandler.getEquippedCurios();
//            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
//                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
//                if (!itemStack.isEmpty()) items.add(itemStack);
//            }
//        });
//        return items;
//    }
//
//    public static <C> ArrayList<C> getCurios(LivingEntity living, Class<C> clazz) {
//        ArrayList<C> items = new ArrayList<>();
//        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
//            IItemHandlerModifiable itemHandlerModifiable = handler.getEquippedCurios();
//            for (int i = 0; i < itemHandlerModifiable.getSlots(); i++) {
//                ItemStack itemStack = itemHandlerModifiable.getStackInSlot(i);
//                Item item = itemStack.getItem();
//                if (clazz.isInstance(item)) items.add(clazz.cast(item));
//            }
//        });
//        return items;
//    }

//    public static Optional<ItemStack> getSlot(LivingEntity living, String id, int index) {
//        AtomicReference<Optional<ItemStack>> atomic = new AtomicReference<>(Optional.empty());
//        CuriosApi.getCuriosInventory(living).ifPresent(handler -> {
//            Map<String, ICurioStacksHandler> curios = handler.getCurios();
//            ICurioStacksHandler stacksHandler = curios.get(id);
//            if (stacksHandler != null) {
//                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
//                if (index < stackHandler.getSlots()) {
//                    ItemStack stack = stackHandler.getStackInSlot(index);
//                    if (!stack.isEmpty()) atomic.set(Optional.of(stack));
//                }
//            }
//        });
//        return atomic.get();
//    }
}
