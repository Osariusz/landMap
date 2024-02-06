# Land Map
LandMap is a groundbreaking Minecraft minimap mod that offers a unique and unparalleled mapping experience. Unlike most other minimap mods, LandMap goes beyond merely displaying the terrain you have explored. Its primary objective is to provide players with a comprehensive map that showcases not only the known terrain but also vast unexplored areas, revealing whether they consist of land or water.

Imagine having access to a minimap that grants you a bird's-eye view of your surroundings, allowing you to plan your adventures with confidence. LandMap empowers players by offering crucial information about uncharted territories, enabling you to make informed decisions about where to venture next.

One of the significant advantages of LandMap is its ability to highlight water bodies on the map. Whether you're on a quest to find an ocean monument, searching for an ideal location to build a pillager farm, or simply exploring the world, this feature proves invaluable. You can quickly identify large bodies of water and plan your routes accordingly, saving you time and effort in your exploration endeavors.

![Minimap presentation](https://raw.githubusercontent.com/Osariusz/landMap/main/MinimapPresentation.png)

## LandMap Technical Features:

- Server and Client Requirement: LandMap is a mod that is required on both the server and client to ensure seamless synchronization of exploration data and map features across multiplayer environments.

- Biome Based Map: LandMap's minimap is generated based on the water biomes defined in it's config files. It then looks over a large area by only selecting the positions needed to draw a pixel map.

- Map Caching: LandMap includes a map caching feature, allowing for the efficient storage of map data for future use. This not only optimizes performance but also enables players to revisit previous exploration data conveniently.

- Client-Side Rendering with Server Data: LandMap's minimap is rendered on the client side, while all the map data is sent by the server. This approach optimizes performance by distributing the rendering workload to each player's individual client while ensuring that data accuracy is maintained centrally.

- Customization through JSON Configuration: LandMap offers extensive customization options through JSON configuration files. Players can tailor the minimap to their specific preferences by adjusting the map's scale, display options, marker settings, change water biomes, and more
