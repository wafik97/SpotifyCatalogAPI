## Configuration

To configure your data source, follow these steps:

1. Open the `application.properties` file.
2. Set `datasource.type` to either `json` or `spotify_api`, depending on the data source you want to use:

    - `datasource.type=json`: Use the local JSON files as the data source.
    - `datasource.type=spotify_api`: Use the Spotify API for fetching data.

## Running Tests

The tests in this project are divided based on the selected data source.

### When `datasource.type=json`

Only the following tests can be run:

- `AlbumTest`
- `SongTest`
- `ArtistTest`

These tests will interact with the local JSON files to validate the functionality.

### When `datasource.type=spotify_api`

Only the following test can be run:

- `SpotifyAPIServiceTest`

This test will interact with the Spotify API to validate the functionality.

## GitHub Actions Workflow

You can choose which data source to use for running tests via GitHub Actions.

- In GitHub Actions, you can select the "Run Workflow" option and then choose whether to run the tests with the `json` data source or the `spotify_api` data source.
- This makes it easy to switch between local testing and actual API integration.

## Example Usage

### To run the tests with the JSON data source:

1. Set `datasource.type=json` in your `application.properties`.
2. Run the tests: `AlbumTest`, `SongTest`, and `ArtistTest`.

### To run the tests with the Spotify API data source:

1. Set `datasource.type=spotify_api` in your `application.properties`.
2. Run the test: `SpotifyAPIServiceTest`.
