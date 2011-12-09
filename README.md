This is a yet another XMPP bot project.

Bot is based on my needs, and has epwing dictionaries support baked in.

It should have web interface eventually too.

Thank you.

Look for property file in core/main/resources, you should make copy of it there and in tests with name default.props.
In order to epwing tests to pass you should download epwing version of edict, put it somewhere and place line
`edict=<path/to/edict>` in properties file in test/resources