# Running Spark in the Ukko cluster

*You should also read <http://spark.apache.org/docs/1.0.2/spark-standalone.html>, which has been used as a basis for this document.*

1.  Unless you happen to be in a computer that is in CS network – which is unlikely – you can't access Ukko cluster nodes directly. What you'll have to do instead is to connect to one of the CS department shell servers – either `melkinkari.cs.helsinki.fi` or `melkinpaasi.cs.helsinki.fi` with SSH and connect from the Ukko node you have selected from there. 

    When you are logged in either `melkinkari` or `melkinpaasi`, connect to an Ukko node you want to use with `ssh ukkoXXX.hpc.cs.helsinki.fi`. List of Ukko nodes is here: <http://www.cs.helsinki.fi/ukko/hpc-report.txt> – pick one that is not reserved and has a low load. This Ukko node will be called as a *master node* from here on.

2.  The Spark scripts for starting/stopping your cluster utilize SSH connection to Ukko nodes you want to use as workers. They also require that using `ssh` to connect from your master to these nodes doesn't require a password. You have two options:

    1.  *The right way:* create a SSH key with a passphrase and use `ssh-agent` to make `ssh` to not prompt a password when connecting.
    2.  *The easy way:* create a SSH key without a passphrase.

    *TODO: Add link to guide for generating SSH keys*

    When you're done with your setup, you should test that it's working by making a SSH connection from an Ukko node to an another. If `ssh` doesn't prompt you a password, everything is ok.

3.  Taatto filesystem is mounted in all Ukko nodes, making it ideal place to hold all the things we need for running the Spark cluster. Create a directory under `/cs/taatto/scratch` named after your username – e.g. `/cs/taatto/scratch/your_username`. Note though that as the MOTD in Ukko nodes warn you, files under `/cs/taatto/scratch` will be deleted after a while.

4.  Download Spark distribution to your newly created directory from <http://spark.apache.org/downloads.html> – choose the "Hadoop 1" pre-built package. `wget <url-to-spark-file>` downloads a file to your current directory.

5.  Extract the file you downloaded using `tar zxvf spark-1.0.2-bin-hadoop1.tgz`. **Please make sure you are not extracting it directly to the `/cs/taatto/scratch` or any other place you shouldn't.**

6.  Go to the folder `spark-1.0.2-bin-hadoop1` and edit `conf/slaves` if you want to have more workers than just the one on master node. Put the hostname (ukkoXXX) of every Ukko node you want to use to a new line.

7.  Run `sbin/start-all.sh` - this will start master process on the computer you're running the script from and all the worker processes in all nodes you defined in conf/slaves. The command will give you a path to master log file (something similar to `/cs/taatto/scratch/yourusername/spark/sbin/../logs/spark-yourusername-org.apache.spark.deploy.master.Master-1-ukko067.out`, be sure to pick the master log file, which will be the at the top of the output - don't pick a worker one). Reading this file will likely be helpful in case of trouble.

8.  You should now test if your cluster works with command `MASTER=spark://ukkoXXX:7077 bin/spark-shell`. If it seems that everything is ok, congratulations, your Spark cluster is up and running!

9.  Finally, you can stop the cluster by running `sbin/stop-all.sh` in your master node.

*Note: When Spark cluster is started, it also launches a Web UI, which can be very helpful to see the status of your worker nodes and your application. It, however, is slightly challenging to access if Ukko nodes are unaccessable from your browser. You can:

    1.  Run a browser instance in a server that **does** have a access to Ukko nodes
    2.  Set up a SSH tunnel and access Ukko nodes that way*