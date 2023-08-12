package ir.pasargad.pouyamansournia.barrier.TCP;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSocket {
    public enum ConnectionErrorType {
        UNKNOWN_HOST(0),
        CONNECTION_REFUSED(1),
        UNKNOWN(2);

        private final int value;

        ConnectionErrorType(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            switch (value) {
                case 0:
                    return "Given host is unknown!";
                case 1:
                    return "Connection refused!";
                case 2:
                    return "Unknown error occurred";
            }
            return "";
        }
    }

    public interface TcpSocketEventListener {
        void onConnectionError(ConnectionErrorType connectionErrorType);
        void onSocketConnected();
    }

    class ConnectTask extends AsyncTask<Void, Void, ConnectionErrorType> {

        @Override
        protected ConnectionErrorType doInBackground(Void... params) {
            try {
                InetAddress destAddress = InetAddress.getByName(mIpAddress);
                mSocket = new Socket(destAddress, mPort);
                mOutputStream = mSocket.getOutputStream();
                mOutput = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mOutputStream)));
            } catch (UnknownHostException e) {
                return ConnectionErrorType.UNKNOWN_HOST;
            } catch (IOException e) {
                e.printStackTrace();
                return ConnectionErrorType.CONNECTION_REFUSED;
            }
            return ConnectionErrorType.UNKNOWN;
        }

        @Override
        protected void onPostExecute(ConnectionErrorType connectionErrorType) {
            super.onPostExecute(connectionErrorType);
            if (connectionErrorType != ConnectionErrorType.UNKNOWN) {
                tcpSocketEventListener.onConnectionError(connectionErrorType);
            } else {
                tcpSocketEventListener.onSocketConnected();
            }
        }

    }


    Socket mSocket;
    String mIpAddress;
    int mPort;
    final String TAG = "TcpSocket";
    OutputStream mOutputStream;
    PrintWriter mOutput;
    TcpSocketEventListener tcpSocketEventListener;

    public TCPSocket(String mIpAddress, int mPort, TcpSocketEventListener tcpSocketEventListener) {
        this.mIpAddress = mIpAddress;
        this.mPort = mPort;
        this.tcpSocketEventListener = tcpSocketEventListener;
        ConnectTask connectTask = new ConnectTask();
        connectTask.execute();
    }

    public void connect() {
        ConnectTask connectTask = new ConnectTask();
        connectTask.execute();
    }

    public void disconnect() {
        try {
            mSocket.close();
            mOutput.close();
            mOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(String data) {
        mOutput.print(data);
        mOutput.flush();
    }
}
