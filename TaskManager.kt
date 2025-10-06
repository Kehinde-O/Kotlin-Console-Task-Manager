import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * This is our Task class - it's like a blueprint for creating tasks.
 * 
 * I used a "data class" here because it automatically gives us useful methods
 * like equals(), hashCode(), and toString() for free. It's perfect for storing
 * information about a task like its title, description, and whether it's done.
 * 
 * @property id Each task gets a unique number so we can find it later
 * @property title What the task is called (like "Buy groceries")
 * @property description More details about what needs to be done
 * @property priority How important the task is (LOW, MEDIUM, or HIGH)
 * @property isCompleted Whether we've finished this task or not
 * @property createdAt When we created this task
 */
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    /**
     * This shows the task title, but adds "[COMPLETED]" in front if we're done with it.
     * It's like a smart way to display the title that changes based on whether we finished it.
     */
    val displayTitle: String
        get() = if (isCompleted) "[COMPLETED] $title" else title
    
    /**
     * This gives us a nice text description of what's going on with the task.
     * It tells us if it's done, or if it's high/medium/low priority.
     */
    val status: String
        get() = when {
            isCompleted -> "Completed"
            priority == Priority.HIGH -> "High Priority"
            priority == Priority.MEDIUM -> "Medium Priority"
            else -> "Low Priority"
        }
}

/**
 * This is just a simple list of priority levels we can choose from.
 * 
 * Instead of using strings like "high" or "low" (which could have typos),
 * we use this enum so Kotlin makes sure we only use valid priority levels.
 * It's like having a dropdown menu with only the right options.
 */
enum class Priority {
    LOW, MEDIUM, HIGH
}

/**
 * This is the main class that manages all our tasks.
 * 
 * Think of it like a filing cabinet that can add, remove, and organize tasks.
 * It keeps a list of all tasks in memory and has methods to do things like
 * "add a new task" or "mark a task as done". This is where most of the
 * real work happens in our program.
 */
class TaskManager {
    // This is our list where we store all the tasks
    private val tasks: MutableList<Task> = mutableListOf()
    
    // We need to give each task a unique number, so we keep track of the next number to use
    private var nextId: Int = 1
    
    // Just the name of our app
    private val appName: String = "Kotlin Task Manager"
    
    /**
     * This function creates a new task and adds it to our list.
     * 
     * When someone wants to add a task, we create a new Task object with
     * all the information they provided, give it a unique ID number, and
     * put it in our list so we can find it later.
     * 
     * @param title What the task is called
     * @param description What needs to be done
     * @param priority How important it is
     * @return The new task we just created
     */
    fun addTask(title: String, description: String, priority: Priority): Task {
        // Make a new task with the next available ID number
        val newTask = Task(
            id = nextId++,
            title = title,
            description = description,
            priority = priority
        )
        
        // Add it to our list
        tasks.add(newTask)
        
        println("Task added successfully!")
        return newTask
    }
    
    /**
     * This function deletes a task from our list.
     * 
     * We look through all our tasks to find the one with the matching ID number.
     * If we find it, we remove it from the list. If we don't find it, we tell
     * the user that task doesn't exist.
     * 
     * @param id The ID number of the task to delete
     * @return true if we found and deleted it, false if we couldn't find it
     */
    fun removeTask(id: Int): Boolean {
        // Look for a task with this ID number
        val taskToRemove = tasks.find { it.id == id }
        
        return if (taskToRemove != null) {
            // We found it! Remove it from our list
            tasks.remove(taskToRemove)
            println("Task '$id' removed successfully!")
            true
        } else {
            // Couldn't find a task with that ID
            println("Task with ID '$id' not found!")
            false
        }
    }
    
    /**
     * This function marks a task as "done" or completed.
     * 
     * We find the task by its ID number, then we create a new version of that
     * task that's exactly the same except we set isCompleted to true. This is
     * a cool feature of data classes - we can make a copy with just one thing changed.
     * 
     * @param id The ID number of the task to mark as done
     * @return true if we found and completed it, false if we couldn't find it
     */
    fun completeTask(id: Int): Boolean {
        // Find which position in our list this task is at
        val taskIndex = tasks.indexOfFirst { it.id == id }
        
        return if (taskIndex != -1) {
            // Make a copy of the task but with isCompleted set to true
            val updatedTask = tasks[taskIndex].copy(isCompleted = true)
            tasks[taskIndex] = updatedTask
            println("Task '$id' marked as completed!")
            true
        } else {
            println("Task with ID '$id' not found!")
            false
        }
    }
    
    /**
     * This function shows all our tasks, with options to filter them.
     * 
     * We can show all tasks, just the completed ones, just the pending ones,
     * or just the high priority ones. Then we print them out in a nice format
     * so the user can see all the details.
     * 
     * @param filter What kind of tasks to show (all, completed, pending, or high priority)
     */
    fun listTasks(filter: TaskFilter = TaskFilter.ALL) {
        // Figure out which tasks to show based on the filter
        val filteredTasks = when (filter) {
            TaskFilter.ALL -> tasks
            TaskFilter.COMPLETED -> tasks.filter { it.isCompleted }
            TaskFilter.PENDING -> tasks.filter { !it.isCompleted }
            TaskFilter.HIGH_PRIORITY -> tasks.filter { it.priority == Priority.HIGH }
        }
        
        if (filteredTasks.isEmpty()) {
            println("No tasks found for the selected filter.")
            return
        }
        
        println("\nTask List (${filteredTasks.size} tasks):")
        println("=" * 50)
        
        // Go through each task and print its details
        for ((index, task) in filteredTasks.withIndex()) {
            val taskNumber = index + 1
            val formattedDate = task.createdAt.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"))
            
            println("$taskNumber. ${task.displayTitle}")
            println("   Description: ${task.description}")
            println("   Priority: ${task.status}")
            println("   Created: $formattedDate")
            println("   ID: ${task.id}")
            println("-" * 30)
        }
    }
    
    /**
     * This function calculates some interesting numbers about our tasks.
     * 
     * It counts how many tasks we have total, how many are done, how many
     * are still pending, and how many are high priority. This gives us
     * a nice overview of our task situation.
     * 
     * @return A bunch of numbers about our tasks
     */
    fun getStatistics(): TaskStatistics {
        // Count up all the different types of tasks
        val totalTasks = tasks.size
        val completedTasks = tasks.count { it.isCompleted }
        val pendingTasks = totalTasks - completedTasks
        val highPriorityTasks = tasks.count { it.priority == Priority.HIGH }
        
        return TaskStatistics(totalTasks, completedTasks, pendingTasks, highPriorityTasks)
    }
    
    /**
     * This function lets us search for tasks by typing in some words.
     * 
     * We look through all our tasks and see if the search words appear in
     * either the title or description. We make it case-insensitive so
     * "grocery" will find "Grocery" or "GROCERY".
     * 
     * @param query The words to search for
     * @return A list of tasks that match what we're looking for
     */
    fun searchTasks(query: String): List<Task> {
        val lowercaseQuery = query.lowercase()
        
        return tasks.filter { task ->
            task.title.lowercase().contains(lowercaseQuery) ||
            task.description.lowercase().contains(lowercaseQuery)
        }
    }
}

/**
 * This class holds all the numbers about our tasks.
 * 
 * It's like a report card that tells us how we're doing with our tasks.
 * We can see how many we have total, how many we've finished, and
 * what percentage we've completed.
 * 
 * @property totalTasks How many tasks we have altogether
 * @property completedTasks How many we've finished
 * @property pendingTasks How many we still need to do
 * @property highPriorityTasks How many are really important
 */
data class TaskStatistics(
    val totalTasks: Int,
    val completedTasks: Int,
    val pendingTasks: Int,
    val highPriorityTasks: Int
) {
    /**
     * This calculates what percentage of our tasks we've finished.
     * 
     * It's like getting a grade - if we have 10 tasks and finished 7,
     * we get 70%. If we have no tasks, we get 0%.
     */
    val completionPercentage: Double
        get() = if (totalTasks > 0) (completedTasks.toDouble() / totalTasks) * 100 else 0.0
}

/**
 * This is just a list of different ways we can filter our tasks.
 * 
 * Sometimes we want to see all tasks, sometimes just the ones we've finished,
 * sometimes just the ones we still need to do, and sometimes just the really
 * important ones. This enum gives us those options.
 */
enum class TaskFilter {
    ALL, COMPLETED, PENDING, HIGH_PRIORITY
}

/**
 * This is the main class that handles talking to the user.
 * 
 * It's like the front desk of our task manager - it shows the menu,
 * gets what the user wants to do, and then calls the right functions
 * to make it happen. It keeps running until the user decides to quit.
 */
class TaskManagerApp {
    private val taskManager = TaskManager()
    private var isRunning = true
    
    /**
     * This is the main function that starts everything up.
     * 
     * It shows a welcome message, then keeps showing the menu and
     * doing what the user asks until they decide to quit. It's like
     * the main loop of our program.
     */
    fun run() {
        println("Welcome to the Kotlin Task Manager!")
        println("=" * 40)
        
        // Keep running until the user wants to quit
        while (isRunning) {
            displayMenu()
            val choice = readLine()?.trim() ?: ""
            
            // Figure out what the user wants to do
            when (choice) {
                "1" -> addTaskMenu()
                "2" -> removeTaskMenu()
                "3" -> completeTaskMenu()
                "4" -> listTasksMenu()
                "5" -> searchTasksMenu()
                "6" -> showStatistics()
                "7" -> {
                    println("Thank you for using the Task Manager!")
                    isRunning = false
                }
                else -> println("Invalid choice. Please try again.")
            }
            
            if (isRunning) {
                println("\nPress Enter to continue...")
                readLine()
            }
        }
    }
    
    /**
     * This function shows the menu to the user.
     * 
     * It prints out all the options they can choose from in a nice format.
     * The user can pick a number to tell us what they want to do.
     */
    private fun displayMenu() {
        println("\n" + "=" * 50)
        println("TASK MANAGER MENU")
        println("=" * 50)
        println("1. Add Task")
        println("2. Remove Task")
        println("3. Complete Task")
        println("4. List Tasks")
        println("5. Search Tasks")
        println("6. Show Statistics")
        println("7. Exit")
        println("=" * 50)
        print("Enter your choice (1-7): ")
    }
    
    /**
     * This function helps the user add a new task.
     * 
     * We ask them for the title, description, and priority level.
     * If they don't give us a title, we tell them they have to.
     * If they pick an invalid priority, we just use Medium as the default.
     */
    private fun addTaskMenu() {
        println("\nADD NEW TASK")
        println("-" * 20)
        
        print("Enter task title: ")
        val title = readLine()?.trim() ?: ""
        
        if (title.isEmpty()) {
            println("Title cannot be empty!")
            return
        }
        
        print("Enter task description: ")
        val description = readLine()?.trim() ?: ""
        
        println("\nSelect priority:")
        println("1. Low")
        println("2. Medium")
        println("3. High")
        print("Enter priority (1-3): ")
        
        val priorityChoice = readLine()?.trim() ?: ""
        val priority = when (priorityChoice) {
            "1" -> Priority.LOW
            "2" -> Priority.MEDIUM
            "3" -> Priority.HIGH
            else -> {
                println("Invalid priority choice. Defaulting to Medium.")
                Priority.MEDIUM
            }
        }
        
        taskManager.addTask(title, description, priority)
    }
    
    /**
     * This function helps the user delete a task.
     * 
     * We ask them for the task ID number. If they type something that's not
     * a number, we tell them it's invalid and don't try to delete anything.
     */
    private fun removeTaskMenu() {
        println("\nREMOVE TASK")
        println("-" * 20)
        
        print("Enter task ID to remove: ")
        val input = readLine()?.trim() ?: ""
        val id = input.toIntOrNull()
        
        if (id == null) {
            println("Invalid task ID!")
            return
        }
        
        taskManager.removeTask(id)
    }
    
    /**
     * This function helps the user mark a task as done.
     * 
     * We ask them for the task ID number. If they type something that's not
     * a number, we tell them it's invalid and don't try to complete anything.
     */
    private fun completeTaskMenu() {
        println("\nCOMPLETE TASK")
        println("-" * 20)
        
        print("Enter task ID to complete: ")
        val input = readLine()?.trim() ?: ""
        val id = input.toIntOrNull()
        
        if (id == null) {
            println("Invalid task ID!")
            return
        }
        
        taskManager.completeTask(id)
    }
    
    /**
     * This function helps the user see their tasks with different filters.
     * 
     * They can choose to see all tasks, just the completed ones, just the
     * pending ones, or just the high priority ones. If they pick something
     * invalid, we just show all tasks.
     */
    private fun listTasksMenu() {
        println("\nLIST TASKS")
        println("-" * 20)
        println("1. All Tasks")
        println("2. Completed Tasks")
        println("3. Pending Tasks")
        println("4. High Priority Tasks")
        print("Select filter (1-4): ")
        
        val choice = readLine()?.trim() ?: ""
        val filter = when (choice) {
            "1" -> TaskFilter.ALL
            "2" -> TaskFilter.COMPLETED
            "3" -> TaskFilter.PENDING
            "4" -> TaskFilter.HIGH_PRIORITY
            else -> {
                println("Invalid choice. Showing all tasks.")
                TaskFilter.ALL
            }
        }
        
        taskManager.listTasks(filter)
    }
    
    /**
     * This function helps the user search for tasks.
     * 
     * They can type in some words and we'll look through all the task titles
     * and descriptions to find matches. If we don't find anything, we tell them.
     * If we find some, we show them in a nice list.
     */
    private fun searchTasksMenu() {
        println("\nSEARCH TASKS")
        println("-" * 20)
        
        print("Enter search query: ")
        val query = readLine()?.trim() ?: ""
        
        if (query.isEmpty()) {
            println("Search query cannot be empty!")
            return
        }
        
        val results = taskManager.searchTasks(query)
        
        if (results.isEmpty()) {
            println("No tasks found matching '$query'")
        } else {
            println("Found ${results.size} task(s) matching '$query':")
            println("=" * 40)
            
            for ((index, task) in results.withIndex()) {
                val taskNumber = index + 1
                println("$taskNumber. ${task.displayTitle}")
                println("   Description: ${task.description}")
                println("   Priority: ${task.status}")
                println("-" * 20)
            }
        }
    }
    
    /**
     * This function shows the user some interesting numbers about their tasks.
     * 
     * We calculate how many tasks they have, how many they've finished,
     * what percentage they've completed, and give them a little motivational
     * message based on how well they're doing.
     */
    private fun showStatistics() {
        println("\nTASK STATISTICS")
        println("-" * 20)
        
        val stats = taskManager.getStatistics()
        
        println("Task Overview:")
        println("   Total Tasks: ${stats.totalTasks}")
        println("   Completed: ${stats.completedTasks}")
        println("   Pending: ${stats.pendingTasks}")
        println("   High Priority: ${stats.highPriorityTasks}")
        println("   Completion Rate: ${String.format("%.1f", stats.completionPercentage)}%")
        
        // Give them a nice message based on how well they're doing
        val productivityMessage = when {
            stats.completionPercentage >= 80 -> "Excellent productivity!"
            stats.completionPercentage >= 60 -> "Good progress!"
            stats.completionPercentage >= 40 -> "Keep going!"
            else -> "You can do it!"
        }
        
        println("   Status: $productivityMessage")
    }
}

/**
 * This is a cool trick that lets us repeat a string multiple times.
 * 
 * Instead of writing "=" * 50, we can just write "=" * 50 and it will
 * give us 50 equal signs. It's like a shortcut for making lines and borders.
 * 
 * @param n How many times to repeat the string
 * @return The string repeated that many times
 */
operator fun String.times(n: Int): String = this.repeat(n)

/**
 * This is where our program starts when we run it.
 * 
 * We create our task manager app and then start it running.
 * Everything begins here!
 */
fun main() {
    // Make a new task manager app and start it up
    val app = TaskManagerApp()
    app.run()
}
