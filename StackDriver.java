public class StackDriver implements StackDriverInterface {
    private static TestTimes runTime = new TestTimes();

    public static void main(String[] args) {
        StackDriver stackDriver = new StackDriver();
        String[] testTypes = {"Push", "Pop"};

        for (String testType : testTypes) {
            System.out.println("Test Type = " + testType + "\n");

            // Test Times Section
            printTestTimeHeaders();
            printTestTimes(stackDriver, testTypes);

            System.out.println(); // Separator between test times and memory usage

            // Memory Usage Section
            printMemoryUsageHeaders();
            printMemoryUsage(stackDriver, testTypes);

            System.out.println();  // Blank line between different test types
        }
    }

    private static void printTestTimeHeaders() {
        System.out.printf("%-16s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%n", "", "Run 1", "Run 2", "Run 3", "Run 4", "Run 5", "Run 6", "Run 7", "Run 8", "Run 9", "Run 10", "Average");
        System.out.printf("%-16s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%n", "", "Micro", "Micro", "Micro", "Micro", "Micro", "Micro", "Micro", "Micro", "Micro", "Micro", "Micro");
        System.out.printf("%-16s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%n", "", "Seconds", "Seconds", "Seconds", "Seconds", "Seconds", "Seconds", "Seconds", "Seconds", "Seconds", "Seconds", "Seconds");
        for (int i = 0; i < 112; i++) {
            System.out.print("-");
        }
        System.out.println(); // Move to the next line after printing the dashes
    }

    private static void printTestTimes(StackDriver stackDriver, String[] testTypes) {
        for (StackType stackType : StackType.values()) {
            System.out.println(stackType.name() + "\n");
            Object[] results = stackDriver.runTestCase(stackType, StackDriverInterface.TestType.valueOf(testTypes[0]), 10); // Assuming the first testType for simplicity
            double averageTime = 0;
            for (int i = 0; i < 10; i++) {
                double time = ((TestTimes) results[results.length - 1]).getTestTimes()[i];
                System.out.printf("%-16s%-12.3f", "Run " + (i + 1), time / 1_000.0);
                averageTime += time;
            }
            averageTime /= 10.0;
            System.out.printf("%-12.3f%n", averageTime / 1_000.0);
            System.out.println();
        }
    }

    private static void printMemoryUsageHeaders() {
        System.out.printf("%-16s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%n", "", "Run 1", "Run 2", "Run 3", "Run 4", "Run 5", "Run 6", "Run 7", "Run 8", "Run 9", "Run 10", "Average");
        System.out.printf("%-16s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%n", "", "Kilo", "Kilo", "Kilo", "Kilo", "Kilo", "Kilo", "Kilo", "Kilo", "Kilo", "Kilo", "Kilo");
        System.out.printf("%-16s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%-12s%n", "", "Bytes", "Bytes", "Bytes", "Bytes", "Bytes", "Bytes", "Bytes", "Bytes", "Bytes", "Bytes", "Bytes");
        for (int i = 0; i < 112; i++) {
            System.out.print("-");
        }
        System.out.println(); // Move to the next line after printing the dashes
    }

    private static void printMemoryUsage(StackDriver stackDriver, String[] testTypes) {
        for (StackType stackType : StackType.values()) {
            System.out.println(stackType.name() + "\n");
            Object[] results = stackDriver.runTestCase(stackType, StackDriverInterface.TestType.valueOf(testTypes[0]), 10); // Assuming the first testType for simplicity
            double averageMemory = 0;
            for (int i = 0; i < 10; i++) {
                // Convert memory usage from bytes to kilobytes by dividing by 1024
                double memoryInKiloBytes = ((TestTimes) results[results.length - 1]).getMemoryUsages()[i] / 1024;
                System.out.printf("%-16s%-12.3f", "Run " + (i + 1), memoryInKiloBytes);
                averageMemory += memoryInKiloBytes;
            }
            averageMemory /= 10.0; // Calculate the average memory usage in kilobytes
            System.out.printf("%-12.3f%n", averageMemory); // Print the average memory usage
            System.out.println();
        }
    }

    @Override
    public StackInterface<String> createStack(StackType stackType, TestType stackTestType) {
        switch (stackType) {
            case ArrayBasedStack:
                ArrayBasedStack<String> arrayBasedStack = new ArrayBasedStack<>();
                if (stackTestType == TestType.Pop) {
                    for (int i = 1; i <= 10000; i++) {
                        arrayBasedStack.push("String " + i);
                    }
                }
                return arrayBasedStack;
            case LinkedStack:
                LinkedStack<String> linkedStack = new LinkedStack<>();
                if (stackTestType == TestType.Pop) {
                    for (int i = 1; i <= 10000; i++) {
                        linkedStack.push("String " + i);
                    }
                }
                return linkedStack;
            default:
                return null;
        }
    }

    @Override
    public Object[] runTestCase(StackType stackType, TestType stackTestType, int numberOfTimes) {
        runTime.resetTestTimes();
        Object[] results = new Object[numberOfTimes * 2 + 1];

        for (int i = 0; i < numberOfTimes; i++) {
            StackInterface<String> stack = createStack(stackType, stackTestType);
            StackInterface<String> initialStack = stack.copy();

            long startTime = System.nanoTime();

            switch (stackTestType) {
                case Push:
                    for (int j = 1; j <= 10000; j++) {
                        stack.push("String " + j);
                    }
                    break;
                case Pop:
                    while (!stack.isEmpty()) {
                        stack.pop(); // Pop all elements to empty the stack
                    }
                    break;
                default:
                    break;
            }

            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            runTime.addTestTime(elapsedTime);

            StackInterface<String> finalStack = stack.copy();
            results[i * 2] = initialStack;
            results[i * 2 + 1] = finalStack;
        }

        results[results.length - 1] = runTime;
        return results;
    }
}

