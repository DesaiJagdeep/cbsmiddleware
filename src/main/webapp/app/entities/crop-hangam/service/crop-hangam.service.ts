import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICropHangam, NewCropHangam } from '../crop-hangam.model';

export type PartialUpdateCropHangam = Partial<ICropHangam> & Pick<ICropHangam, 'id'>;

export type EntityResponseType = HttpResponse<ICropHangam>;
export type EntityArrayResponseType = HttpResponse<ICropHangam[]>;

@Injectable({ providedIn: 'root' })
export class CropHangamService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/crop-hangams');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cropHangam: NewCropHangam): Observable<EntityResponseType> {
    return this.http.post<ICropHangam>(this.resourceUrl, cropHangam, { observe: 'response' });
  }

  update(cropHangam: ICropHangam): Observable<EntityResponseType> {
    return this.http.put<ICropHangam>(`${this.resourceUrl}/${this.getCropHangamIdentifier(cropHangam)}`, cropHangam, {
      observe: 'response',
    });
  }

  partialUpdate(cropHangam: PartialUpdateCropHangam): Observable<EntityResponseType> {
    return this.http.patch<ICropHangam>(`${this.resourceUrl}/${this.getCropHangamIdentifier(cropHangam)}`, cropHangam, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICropHangam>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICropHangam[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCropHangamIdentifier(cropHangam: Pick<ICropHangam, 'id'>): number {
    return cropHangam.id;
  }

  compareCropHangam(o1: Pick<ICropHangam, 'id'> | null, o2: Pick<ICropHangam, 'id'> | null): boolean {
    return o1 && o2 ? this.getCropHangamIdentifier(o1) === this.getCropHangamIdentifier(o2) : o1 === o2;
  }

  addCropHangamToCollectionIfMissing<Type extends Pick<ICropHangam, 'id'>>(
    cropHangamCollection: Type[],
    ...cropHangamsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cropHangams: Type[] = cropHangamsToCheck.filter(isPresent);
    if (cropHangams.length > 0) {
      const cropHangamCollectionIdentifiers = cropHangamCollection.map(cropHangamItem => this.getCropHangamIdentifier(cropHangamItem)!);
      const cropHangamsToAdd = cropHangams.filter(cropHangamItem => {
        const cropHangamIdentifier = this.getCropHangamIdentifier(cropHangamItem);
        if (cropHangamCollectionIdentifiers.includes(cropHangamIdentifier)) {
          return false;
        }
        cropHangamCollectionIdentifiers.push(cropHangamIdentifier);
        return true;
      });
      return [...cropHangamsToAdd, ...cropHangamCollection];
    }
    return cropHangamCollection;
  }
}
