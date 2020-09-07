package org.magnum.dataup;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VideoService {

    static final AtomicLong currentID = new AtomicLong(0L);
    Map<Long,Video> videoMaps = new HashMap<Long, Video>();
    List<Video> videoLists = new ArrayList<Video>();

    public VideoService() throws IOException {
    }

    /**
     * 1. Create videoID by calling SetId() method. [ID should not be 0].
     * 2. Create a Map<Long Id,Video v>. Put video object along with ID as a key inside Map.
     * 3. Generate the full URL by passing ID.
     * 4. Create a collection class and store video object inside the collection.
     * 5. Return video object.
     * @param video
     * @return
     */
    public Video postVideo(Video video){
        checkAndSetId(video);
        videoMaps.put(video.getId(),video);
        String dataURL = getDataUrl(video.getId());
        video.setDataUrl(dataURL);

        videoLists.add(video);
        return video;
    }

    /**
     * Impl step 1. Method to creat ID for video.
     */
    public void checkAndSetId(Video entity) {
        if(entity.getId() == 0){
            entity.setId(currentID.incrementAndGet());
        }
    }

    /**
     * Impl Step 3. Pass videoID as an args. Return VideoURL as a String Param.
     * @param videoId
     * @return
     */
    public String getDataUrl(long videoId){
        String url = getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
        return url;
    }

    private String getUrlBaseForLocalServer() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String base =
                "http://"+request.getServerName()
                        + ((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
        return base;
    }

    //============================================================================
    //============================================================================

    /**
     * 6. Return Collection of video.
     * @return
     */
    public Collection<Video> getVideoList(){
        return videoLists;
    }

    //============================================================================
    //============================================================================

    /**
     * Steps to Upload Video Data into Server.
     *
     * 1. Use Post method to upload Video metadata (Already impl).
     * 2. Use ID of Video Metadata, and MultipartFile type data of End point to upload a video binary data.
     * 3. Use FileManager.saveSomeVideo() to save the video. get() to get object of the class.
     * 4. Return State = VideoState.READY if video uploaded successfully. Else Return VideoState.PROCESSING.
     * @param id
     */
    public VideoStatus srvPostVideo(Long id, MultipartFile videoData, HttpServletResponse response) throws IOException {

        VideoStatus videoStatus = new VideoStatus(VideoStatus.VideoState.PROCESSING);
        if(videoData != null){
            try {
                VideoFileManager videoDataManager = VideoFileManager.get();
                videoDataManager.saveVideoData(videoMaps.get(id), (InputStream) videoData);
                videoStatus = new VideoStatus(VideoStatus.VideoState.READY);
                return videoStatus;
            }
            catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                e.printStackTrace();
            }
        }
        else
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        //response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return videoStatus;
    }

    public HttpServletResponse getVideoData(Long id, HttpServletResponse response){

        try {
            Video video = videoMaps.get(id);
            VideoFileManager dataMgr = VideoFileManager.get();

            if(video == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            else if(response.getContentType() == null){
                response.setContentType("video/mp4");
                dataMgr.copyVideoData(video,response.getOutputStream());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
