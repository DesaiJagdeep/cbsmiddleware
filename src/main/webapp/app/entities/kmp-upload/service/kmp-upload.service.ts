import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKMPUpload, NewKMPUpload } from '../kmp-upload.model';

export type PartialUpdateKMPUpload = Partial<IKMPUpload> & Pick<IKMPUpload, 'id'>;

export type EntityResponseType = HttpResponse<IKMPUpload>;
export type EntityArrayResponseType = HttpResponse<IKMPUpload[]>;

@Injectable({ providedIn: 'root' })
export class KMPUploadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/kmp-uploads');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(kMPUpload: NewKMPUpload): Observable<EntityResponseType> {
    return this.http.post<IKMPUpload>(this.resourceUrl, kMPUpload, { observe: 'response' });
  }

  update(kMPUpload: IKMPUpload): Observable<EntityResponseType> {
    return this.http.put<IKMPUpload>(`${this.resourceUrl}/${this.getKMPUploadIdentifier(kMPUpload)}`, kMPUpload, { observe: 'response' });
  }

  partialUpdate(kMPUpload: PartialUpdateKMPUpload): Observable<EntityResponseType> {
    return this.http.patch<IKMPUpload>(`${this.resourceUrl}/${this.getKMPUploadIdentifier(kMPUpload)}`, kMPUpload, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKMPUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKMPUpload[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKMPUploadIdentifier(kMPUpload: Pick<IKMPUpload, 'id'>): number {
    return kMPUpload.id;
  }

  compareKMPUpload(o1: Pick<IKMPUpload, 'id'> | null, o2: Pick<IKMPUpload, 'id'> | null): boolean {
    return o1 && o2 ? this.getKMPUploadIdentifier(o1) === this.getKMPUploadIdentifier(o2) : o1 === o2;
  }

  addKMPUploadToCollectionIfMissing<Type extends Pick<IKMPUpload, 'id'>>(
    kMPUploadCollection: Type[],
    ...kMPUploadsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const kMPUploads: Type[] = kMPUploadsToCheck.filter(isPresent);
    if (kMPUploads.length > 0) {
      const kMPUploadCollectionIdentifiers = kMPUploadCollection.map(kMPUploadItem => this.getKMPUploadIdentifier(kMPUploadItem)!);
      const kMPUploadsToAdd = kMPUploads.filter(kMPUploadItem => {
        const kMPUploadIdentifier = this.getKMPUploadIdentifier(kMPUploadItem);
        if (kMPUploadCollectionIdentifiers.includes(kMPUploadIdentifier)) {
          return false;
        }
        kMPUploadCollectionIdentifiers.push(kMPUploadIdentifier);
        return true;
      });
      return [...kMPUploadsToAdd, ...kMPUploadCollection];
    }
    return kMPUploadCollection;
  }
}
