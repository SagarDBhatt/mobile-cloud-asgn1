/*
 *
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.magnum.dataup;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit.client.Response;
import retrofit.http.Multipart;
import retrofit.mime.TypedFile;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
public class ControllerClass implements VideoSvcApi {

    /**
     * You will need to create one or more Spring controllers to fulfill the
     * requirements of the assignment. If you use this file, please rename it
     * to something other than "AnEmptyController"
     * <p>
     * <p>
     * ________  ________  ________  ________          ___       ___  ___  ________  ___  __
     * |\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \
     * \ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_
     * \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \
     * \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \
     * \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
     * \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
     */

    @Autowired
    VideoService videoService;


    @Override
    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.GET)
    public @ResponseBody List<Video> getVideoList() {
        return videoService.getVideoList();
    }

    @Override
    @RequestMapping(value = VIDEO_SVC_PATH, method = RequestMethod.POST)
    public @ResponseBody Video addVideo(@RequestBody Video v) {
        return videoService.postVideo(v);
    }

    @Override
    public VideoStatus setVideoData(long id, TypedFile videoData) {
        return null;
    }

    @Override
    public Response getData(long id) {
        return null;
    }

/*

    @RequestMapping(value = "/video", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces ={MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Video> postVideo(@RequestBody Video v) {
        ResponseEntity<Video> respVideo = new ResponseEntity<>(videoService.postVideo(v), HttpStatus.OK);
        return respVideo;
    }


    @ResponseBody
    @RequestMapping(value = "/video",method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Collection<Video>> getVideoList(){
        ResponseEntity<Collection<Video>> videoList = new ResponseEntity<>(videoService.getVideoList(), HttpStatus.OK);
        return videoList;
    }


    @ResponseBody
    @RequestMapping(value = "/video/{id}/data", method = RequestMethod.POST,
                    consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE})
    public VideoStatus postVideoData(@PathVariable(value = "id") Long id, @RequestParam(value = "data") MultipartFile videoData,
                                     HttpServletResponse response) throws IOException {
        return videoService.srvPostVideo(id,videoData,response);
    }

    @ResponseBody
    @GetMapping(value = "/video/{id}/data")
    public HttpServletResponse getVideoData(@PathVariable(value = "id") Long id, HttpServletResponse response){
           return videoService.getVideoData(id,response);
    }

*/

}
