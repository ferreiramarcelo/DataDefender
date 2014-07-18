package com.strider.dataanonymizer;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

import org.apache.log4j.Logger;

import com.strider.dataanonymizer.IDiscoverer;
import com.strider.dataanonymizer.ColumnDiscoverer;

/**
 * Entry point to Data Discoverer utility. 
 * @author Armenak Grigoryan
 */
public class Discoverer {
    
    static Logger log = Logger.getLogger(Discoverer.class);
    
    public static void main( String[] args )
    throws Exception {

        if (args.length == 0 ) {
            log.info("To display usage info please type");
            log.info("    java -jar DataAnonymizer.jar com.strider.Discoverer help");
            return;
        }        

        final Options options = createOptions();
        final CommandLine line = getCommandLine(options, args);
        if (line.hasOption("help")) {
            help(options); 
            return;
        } else if (line.hasOption("c")) {
                log.info("Column discovery in process");
                String databasePropertyFile = "db.properties";
                if (line.hasOption("p")) {
                    databasePropertyFile = line.getOptionValue("p");
                } 
                IDiscoverer discoverer = new ColumnDiscoverer();
                discoverer.discover(databasePropertyFile);
        } else if (line.hasOption("d")) {
                log.info("Data discovery in process");
        }
   }
   
  private static CommandLine getCommandLine(final Options options, final String[] args)
    throws Exception {
        final CommandLineParser parser = new GnuParser();
        final CommandLine line;
 
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            help(options);
            throw new Exception("Unable to process command line arguments");
        }
 
        return line;
    }    
    
    @SuppressWarnings("static-access")
    private static Options createOptions() {
        final Options options = new Options();
        options.addOption("help", false, "Display help");
        options.addOption( "c", "columns", false, "discover candidate column names for anonymization based on provided patterns" );
        options.addOption( "d", "data", false, "discover candidate column for anonymization based on semantic algorithms" );
        options.addOption( "p", "property", true, "define property file" );
        return options;
    }
 
    private static void help(final Options options) {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("DataAnonymizer", options);
    }       
}