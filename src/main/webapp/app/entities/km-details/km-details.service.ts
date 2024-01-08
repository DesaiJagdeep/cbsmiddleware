import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { KmDetails } from './km-details.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KmDetailsService {

    private resourceUrl = 'api/km-details';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(kmDetails: KmDetails): Observable<KmDetails> {
        const copy = this.convert(kmDetails);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(kmDetails: KmDetails): Observable<KmDetails> {
        const copy = this.convert(kmDetails);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<KmDetails> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dueDate = this.dateUtils
            .convertDateTimeFromServer(entity.dueDate);
        entity.kmDate = this.dateUtils
            .convertDateTimeFromServer(entity.kmDate);
        entity.kmFromDate = this.dateUtils
            .convertDateTimeFromServer(entity.kmFromDate);
        entity.kmToDate = this.dateUtils
            .convertDateTimeFromServer(entity.kmToDate);
        entity.eAgreementDate = this.dateUtils
            .convertDateTimeFromServer(entity.eAgreementDate);
        entity.bojaDate = this.dateUtils
            .convertDateTimeFromServer(entity.bojaDate);
    }

    private convert(kmDetails: KmDetails): KmDetails {
        const copy: KmDetails = Object.assign({}, kmDetails);

        copy.dueDate = this.dateUtils.toDate(kmDetails.dueDate);

        copy.kmDate = this.dateUtils.toDate(kmDetails.kmDate);

        copy.kmFromDate = this.dateUtils.toDate(kmDetails.kmFromDate);

        copy.kmToDate = this.dateUtils.toDate(kmDetails.kmToDate);

        copy.eAgreementDate = this.dateUtils.toDate(kmDetails.eAgreementDate);

        copy.bojaDate = this.dateUtils.toDate(kmDetails.bojaDate);
        return copy;
    }
}
