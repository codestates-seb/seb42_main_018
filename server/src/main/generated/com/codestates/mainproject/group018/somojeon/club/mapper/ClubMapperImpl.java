package com.codestates.mainproject.group018.somojeon.club.mapper;

import com.codestates.mainproject.group018.somojeon.club.dto.ClubDto;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-13T00:17:09+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.17 (Azul Systems, Inc.)"
)
@Component
public class ClubMapperImpl implements ClubMapper {

    @Override
    public Club clubPostDtoToClub(ClubDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Club club = new Club();

        club.setClubName( requestBody.getClubName() );
        club.setContent( requestBody.getContent() );
        club.setLocal( requestBody.getLocal() );
        club.setPrivate( requestBody.isPrivate() );

        return club;
    }

    @Override
    public Club clubPatchDtoToClub(ClubDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Club club = new Club();

        club.setClubId( requestBody.getClubId() );
        club.setClubName( requestBody.getClubName() );
        club.setContent( requestBody.getContent() );
        club.setLocal( requestBody.getLocal() );
        club.setPrivate( requestBody.isPrivate() );

        return club;
    }

    @Override
    public ClubDto.Response clubResponseDtoToClub(Club club) {
        if ( club == null ) {
            return null;
        }

        ClubDto.Response response = new ClubDto.Response();

        return response;
    }

    @Override
    public List<ClubDto.Response> clubResponseDtosToClub(List<Club> clubList) {
        if ( clubList == null ) {
            return null;
        }

        List<ClubDto.Response> list = new ArrayList<ClubDto.Response>( clubList.size() );
        for ( Club club : clubList ) {
            list.add( clubResponseDtoToClub( club ) );
        }

        return list;
    }
}
