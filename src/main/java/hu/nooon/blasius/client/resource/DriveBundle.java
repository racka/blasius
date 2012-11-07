package hu.nooon.blasius.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.event.shared.EventBus;
import hu.nooon.blasius.client.event.DriveInitEvent;
import hu.nooon.blasius.client.json.drive.DriveFileMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriveBundle {

    private JsArray<DriveFileMeta> driveFiles;
    private Map<DriveFileMeta, List<DriveFileMeta>> hierarchy = new HashMap<DriveFileMeta, List<DriveFileMeta>>();
    private Map<String, String> fileDownloadURLs = new HashMap<String, String>();


    public RequestBuilder initHierarchy(final EventBus eventBus) {
        RequestBuilder request = new RequestBuilder(RequestBuilder.GET, GWT.getModuleBaseURL() + "googledrive?op=list");
        request.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {

                driveFiles = DriveFileMeta.getAsArray(response.getText());

                for (int i = 0; i < driveFiles.length(); i++) {
                    DriveFileMeta meta = driveFiles.get(i);
                    if (meta.getParentId().isEmpty()) {
                        hierarchy.put(meta, new ArrayList<DriveFileMeta>());
                    }
                }

                for (int i = 0; i < driveFiles.length(); i++) {

                    DriveFileMeta meta = driveFiles.get(i);

                    if (!meta.getParentId().isEmpty()) {
                        for (DriveFileMeta parent : hierarchy.keySet()) {
                            if (parent.getId().equals(meta.getParentId())) {
                                hierarchy.get(parent).add(meta);
                                fileDownloadURLs.put(parent.getTitle() + "/" + meta.getTitle(),
                                        "https://docs.google.com/uc?export=download&id=" +
                                        meta.getId());
                                break;
                            }
                        }
                    }
                }

                eventBus.fireEvent(new DriveInitEvent());
            }

            @Override
            public void onError(Request request, Throwable exception) {
            }
        });

        return request;
    }


    public String getFileDownloadURL(String folderName, String fileName) {
        return fileDownloadURLs.get(folderName + "/" + fileName);
    }

    public List<String> getFolderFileURLs(String folderName) {
        List<String> ids = new ArrayList<String>();

        for (String key : fileDownloadURLs.keySet()) {
            if (key.startsWith(folderName + "/")) {
                ids.add(fileDownloadURLs.get(key));
            }
        }

        return ids;
    }


    public String getStreamURLbyName(String folder, String fileName, String mime) {
        return getFileDownloadURL(folder, fileName);

    }

    public String getStreamURLbyId(String fileId, String mime) {
        if (!fileId.isEmpty()) {
            return GWT.getModuleBaseURL() + "googledrive?op=stream&mime=" + mime + "&fileID=" + fileId;
        }

        return "";
    }


}
