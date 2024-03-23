import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICropRateMaster, NewCropRateMaster } from '../crop-rate-master.model';

export type PartialUpdateCropRateMaster = Partial<ICropRateMaster> & Pick<ICropRateMaster, 'id'>;

export type EntityResponseType = HttpResponse<ICropRateMaster>;
export type EntityArrayResponseType = HttpResponse<ICropRateMaster[]>;

@Injectable({ providedIn: 'root' })
export class CropRateMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/crop-rate-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cropRateMaster: NewCropRateMaster): Observable<EntityResponseType> {
    return this.http.post<ICropRateMaster>(this.resourceUrl, cropRateMaster, { observe: 'response' });
  }

  update(cropRateMaster: ICropRateMaster): Observable<EntityResponseType> {
    return this.http.put<ICropRateMaster>(`${this.resourceUrl}/${this.getCropRateMasterIdentifier(cropRateMaster)}`, cropRateMaster, {
      observe: 'response',
    });
  }

  partialUpdate(cropRateMaster: PartialUpdateCropRateMaster): Observable<EntityResponseType> {
    return this.http.patch<ICropRateMaster>(`${this.resourceUrl}/${this.getCropRateMasterIdentifier(cropRateMaster)}`, cropRateMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICropRateMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICropRateMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCropRateMasterIdentifier(cropRateMaster: Pick<ICropRateMaster, 'id'>): number {
    return cropRateMaster.id;
  }

  compareCropRateMaster(o1: Pick<ICropRateMaster, 'id'> | null, o2: Pick<ICropRateMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getCropRateMasterIdentifier(o1) === this.getCropRateMasterIdentifier(o2) : o1 === o2;
  }

  addCropRateMasterToCollectionIfMissing<Type extends Pick<ICropRateMaster, 'id'>>(
    cropRateMasterCollection: Type[],
    ...cropRateMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cropRateMasters: Type[] = cropRateMastersToCheck.filter(isPresent);
    if (cropRateMasters.length > 0) {
      const cropRateMasterCollectionIdentifiers = cropRateMasterCollection.map(
        cropRateMasterItem => this.getCropRateMasterIdentifier(cropRateMasterItem)!
      );
      const cropRateMastersToAdd = cropRateMasters.filter(cropRateMasterItem => {
        const cropRateMasterIdentifier = this.getCropRateMasterIdentifier(cropRateMasterItem);
        if (cropRateMasterCollectionIdentifiers.includes(cropRateMasterIdentifier)) {
          return false;
        }
        cropRateMasterCollectionIdentifiers.push(cropRateMasterIdentifier);
        return true;
      });
      return [...cropRateMastersToAdd, ...cropRateMasterCollection];
    }
    return cropRateMasterCollection;
  }
}
