# Factories
These classes are really important because the entire code uses them to create custom Inventories, ItemStacks and handle many actions. The logic behind these is completely written by me and it is completely commented, so it is easy to understand and to use any feature that each of these implements.
## ItemStackFactory
Used to create custom ItemStacks with custom names, lores, etc.
## InventoryFactory
Used to create Inventories with custom properties and handle slot clicks in an easier way
## MultipleInventoryFactory
Used to create Inventories that have multiple pages. If you have to display an Inventory with a dynamic number of ItemStacks, that can be higher than 54, it is highly recommended to use this instead of default InventoryFactory. It still relies on InventoryFactory functionalities, though.
## InputFactory
Used to handle actions to execute whenever a Player writes something in the server chat
## BlockFactory
Used to spawn 2 ArmorStands over a Block, usually useful to make a Player understand what the specified block does.
