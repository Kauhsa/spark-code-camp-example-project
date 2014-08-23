# Running Spark in the Ukko cluster

*You should also read <http://spark.apache.org/docs/1.0.2/spark-standalone.html>, which has been used as a basis for this document.*

1.  Go with SSH to Melkinkari or Melkinpaasi. Connect to the Ukko node you want to use with `ssh ukkoXXX.hpc.cs.helsinki.fi`. If you don't care which one, CS computers seem to have a script called ukko available, which connects to a low-load, non-reserved node using SSH. List of Ukko nodes is here: <http://www.cs.helsinki.fi/ukko/hpc-report.txt>

2.  Make sure that you have passwordless SSH connections working between Ukko nodes. You can test this by executing `ssh ukkoXXX.hpc.cs.helsinki.fi` when you're connected to an Ukko node. If ssh is asking you a password, your passwordless SSH connection is not set up properly. Without this, worker processes can not be launched from master node conveniently. Passwordless SSH connections involve generating a key with ssh-keygen and appending the resulting public key to `~/.ssh/authorized_keys` file. Instructions (in Finnish): <http://linux.fi/wiki/SSH>

3.  Go to `/cs/taatto/scratch` and make a directory named after your username.

4.  Download Spark to your newly created directory: `http://spark.apache.org/downloads.html` Choose “For Hadoop 1:” (matches with a right maven version) Click with the right button of the mouse “direct file download” and choose “copy link address”. In Ukko go to `/cs/taatto/scratch/yourusername$` and type “wget” + push ctrl + V.

5.  In the same folder type:  `tar zxvf spark-1.0.2-bin-hadoop1.tgz`

6.  Go to the folder `spark-1.0.2-bin-hadoop1` and edit conf/slaves if you want to have more workers than just the one on master node. Put the hostname (ukkoXXX) of every Ukko node you want to use to a new line.

7.  Run `sbin/start-all.sh` - this will start master process on the computer you're running the script from and all the worker processes in all nodes you defined in conf/slaves. The command will give you a path to master log file (something similar to `/cs/taatto/scratch/yourusername/spark/sbin/../logs/spark-yourusername-org.apache.spark.deploy.master.Master-1-ukko067.out`, be sure to pick the master log file, which will be the first in the output - don't pick a worker one). Open the file (if you want, you can monitor changes in the file with `tail -f <path>`)

8.  In the log file, there should be an URL to web UI of master node - something similar to http://ukko067.hpc.cs.helsinki.fi:8080. Open this URL in your browser. You should see:
    -   Spark URL, which should be similar to spark://ukko067.hpc.cs.helsinki.fi:7077, not spark://ukko067
    -   At least one worker node connected to master (this can take a moment)

9.  If all looks ok, congratulations, your Spark cluster is up and running! You can connect to your cluster using spark-shell with `MASTER=spark://ukkoXXX.hpc.cs.helsinki.fi:7077 bin/spark-shell`. This should also work from your local workstation inside CS network if you download and extract the Spark package there. Replace the MASTER with your Spark URL - you can see it from the web UI. When spark-shell has connected, there should be a new task in "Running Applications"-section inside web UI.

10. After placing your jar in `/cs/taatto/scratch/$USER/`, you can submit your application created from the example project using the run-spark102.sh script. To tune the amount of memory allocated to each slave, and other Spark options, read <http://spark.apache.org/docs/latest/submitting-applications.html> and <http://spark.apache.org/docs/latest/configuration.html#spark-properties>

11. Finally, you can stop the cluster by running `sbin/stop-all.sh` in your master node.