package io.github.toniidev.toniifarms.classes.server;

import io.github.toniidev.toniifarms.classes.tasks.MultipleTask;
import io.github.toniidev.toniifarms.classes.tasks.SingleTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ServerPlayer {
    private static final List<ServerPlayer> players = new ArrayList<>();

    private final UUID playerId;
    private final Plugin plugin;

    private int maxSingleTasks = 3;
    private final List<SingleTask> singleTasks = new ArrayList<>();

    private int maxMultipleTasks = 9;
    private final List<MultipleTask> multipleTasks = new ArrayList<>();

    private double money;

    /**
     * Constructs a new ServerPlayer instance.
     *
     * @param player The Bukkit Player.
     * @param plugin The main plugin instance.
     */
    public ServerPlayer(Player player, Plugin plugin) {
        this.playerId = player.getUniqueId();
        this.plugin = plugin;

        startTaskLoops();
    }

    /**
     * Increases the maximum number of single tasks allowed.
     *
     * @return This ServerPlayer instance for chaining.
     */
    public ServerPlayer increaseMaxSingleTaskAmount() {
        this.maxSingleTasks++;
        return this;
    }

    /**
     * Increases the maximum number of multiple tasks allowed.
     *
     * @return This ServerPlayer instance for chaining.
     */
    public ServerPlayer increaseMaxMultipleTaskAmount() {
        this.maxMultipleTasks++;
        return this;
    }

    /**
     * Starts the task generation loops for single and multiple tasks.
     */
    private void startTaskLoops() {
        startTaskLoop(singleTasks, maxSingleTasks, SingleTask::new);
        startTaskLoop(multipleTasks, maxMultipleTasks, MultipleTask::new);
    }

    /**
     * Starts a task loop for adding tasks to the provided list.
     *
     * @param taskList    The list to add tasks to.
     * @param maxTasks    The maximum number of tasks allowed.
     * @param taskFactory A factory method to create new tasks.
     * @param <T>         The type of task.
     */
    private <T> void startTaskLoop(List<T> taskList, int maxTasks, TaskFactory<T> taskFactory) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (taskList.size() < maxTasks) {
                    T newTask = taskFactory.create();
                    if (!taskList.contains(newTask)) {
                        taskList.add(newTask);
                    }
                }
            }
        }.runTaskTimer(plugin, getRandomDelay(), getRandomDelay());
    }

    /**
     * Retrieves the Bukkit Player associated with this ServerPlayer.
     *
     * @return The Player object, or null if the player is offline.
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }

    /**
     * @return The global list of all ServerPlayer instances.
     */
    public static List<ServerPlayer> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Retrieves the ServerPlayer instance for a given Player.
     *
     * @param player The Bukkit Player.
     * @return The ServerPlayer instance, or null if not found.
     */
    public static ServerPlayer getInstance(Player player) {
        return players.stream()
                .filter(serverPlayer -> serverPlayer.getPlayer() != null && serverPlayer.getPlayer().equals(player))
                .findFirst()
                .orElse(null);
    }

    /**
     * Registers a new ServerPlayer instance if not already present.
     *
     * @param player The Bukkit Player.
     * @param plugin The main plugin instance.
     * @return The existing or newly created ServerPlayer.
     */
    public static ServerPlayer registerPlayer(Player player, Plugin plugin) {
        return players.stream()
                .filter(serverPlayer -> serverPlayer.getPlayer() != null && serverPlayer.getPlayer().equals(player))
                .findFirst()
                .orElseGet(() -> {
                    ServerPlayer newPlayer = new ServerPlayer(player, plugin);
                    players.add(newPlayer);
                    return newPlayer;
                });
    }

    /**
     * Removes a ServerPlayer instance from the global list.
     *
     * @param player The Bukkit Player to remove.
     */
    public static void removePlayer(Player player) {
        players.removeIf(serverPlayer -> serverPlayer.getPlayer() != null && serverPlayer.getPlayer().equals(player));
    }

    /**
     * @return An unmodifiable list of single tasks.
     */
    public List<SingleTask> getSingleTasks() {
        return Collections.unmodifiableList(singleTasks);
    }

    /**
     * @return An unmodifiable list of multiple tasks.
     */
    public List<MultipleTask> getMultipleTasks() {
        return Collections.unmodifiableList(multipleTasks);
    }

    /**
     * @return The maximum number of single tasks allowed.
     */
    public int getMaxSingleTasks() {
        return maxSingleTasks;
    }

    /**
     * @return The maximum number of multiple tasks allowed.
     */
    public int getMaxMultipleTasks() {
        return maxMultipleTasks;
    }

    /**
     * @return The player's money balance.
     */
    public double getMoney() {
        return money;
    }

    /**
     * Generates a random delay for task scheduling.
     *
     * @return A random long value between 60 and 120 ticks.
     */
    private long getRandomDelay() {
        return ThreadLocalRandom.current().nextLong(60, 120); // 60 to 120 ticks (3-6 seconds)
    }

    /**
     * Functional interface for task creation.
     *
     * @param <T> The type of task.
     */
    @FunctionalInterface
    private interface TaskFactory<T> {
        T create();
    }

    /**
     * Adds the specified amount of money to the player's balance.
     *
     * @param amount The amount to add (must be positive).
     * @return This ServerPlayer instance for chaining.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public ServerPlayer addMoney(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add cannot be negative.");
        }
        this.money += amount;
        return this;
    }

    /**
     * Removes the specified amount of money from the player's balance.
     *
     * @param amount The amount to remove (must be positive and not exceed current balance).
     * @return This ServerPlayer instance for chaining.
     * @throws IllegalArgumentException if the amount is negative or exceeds the current balance.
     */
    public ServerPlayer removeMoney(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to remove cannot be negative.");
        }
        if (amount > this.money) {
            throw new IllegalArgumentException("Insufficient balance. Cannot remove more money than available.");
        }
        this.money -= amount;
        return this;
    }
}